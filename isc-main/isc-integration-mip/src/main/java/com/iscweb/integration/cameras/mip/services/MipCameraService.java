package com.iscweb.integration.cameras.mip.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.payload.StringPayload;
import com.iscweb.common.exception.ImageServerException;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.exception.UnableToConnectException;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.model.dto.entity.core.CameraGroupDto;
import com.iscweb.common.model.dto.entity.core.CameraStreamDto;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.camera.BaseCameraEvent;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.integration.camera.ICameraService;
import com.iscweb.common.service.integration.camera.ICameraStream;
import com.iscweb.common.util.EventUtils;
import com.iscweb.integration.cameras.mip.dto.CameraInfoDto;
import com.iscweb.integration.cameras.mip.events.MipCameraDeviceEvent;
import com.iscweb.integration.cameras.mip.services.mip.IRecorderStatusService;
import com.iscweb.integration.cameras.mip.services.mip.IServerCommandService;
import com.iscweb.integration.cameras.mip.services.streaming.CameraEventListener;
import com.iscweb.integration.cameras.mip.services.streaming.ImageServerConnection;
import com.iscweb.integration.cameras.mip.services.streaming.commands.ConnectCommand;
import com.iscweb.integration.cameras.mip.services.streaming.commands.ConnectResponse;
import com.iscweb.integration.cameras.mip.services.streaming.dto.Transcode;
import com.iscweb.integration.cameras.mip.services.streaming.events.MipCameraStreamingStoppedEventDto;
import com.iscweb.integration.cameras.mip.services.streaming.events.MipCameraEventDto;
import com.iscweb.integration.cameras.mip.services.streaming.events.MipCameraStatusEventDto;
import com.mip.command.ArrayOfCameraGroupInfo;
import com.mip.command.ArrayOfCameraInfo;
import com.mip.command.ArrayOfRecorderInfo;
import com.mip.command.ArrayOfStreamInfo;
import com.mip.command.ArrayOfguid;
import com.mip.command.CameraGroupInfo;
import com.mip.command.CameraInfo;
import com.mip.command.CameraSecurityInfo;
import com.mip.command.ConfigurationInfo;
import com.mip.command.RecorderInfo;
import com.mip.command.StreamInfo;
import com.mip.recorderStatus.CameraDeviceStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.transport.http.HTTPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

@Slf4j
@Service("MIPCameraService")
public class MipCameraService implements ICameraService, CameraEventListener<MipCameraEventDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private MipTokenService tokenService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IServerCommandService serverCommandService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IRecorderStatusService recorderStatusService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IEventHub eventHub;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ObjectMapper jsonMapper;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private MipCameraServiceFactory cameraServiceFactory;

    @Getter
    @Setter(onMethod = @__({@Value("${cameras.active.events.batchSize:#{100}}")}))
    private int batchSize;

    private final AtomicReference<ConfigurationInfo> serverCache = new AtomicReference<>(null);
    private final AtomicReference<Map<String, CameraInfoDto>> cameraCache = new AtomicReference<>(null);

    @Override
    public String serviceInfo() {
        log.debug("");
        log.debug("===============================================");
        log.debug("");
        ConfigurationInfo config = serverCache.get();
        if (config == null) {
            synchronized (serverCache) {
                config = serverCache.get();
                if (config == null) {
                    config = serverCommandService.getConfiguration(tokenService.currentToken());
                    serverCache.set(config);
                }
            }
        }

        try {
            getCameraServiceFactory().resolveServices();
        } catch (Exception e) {
            log.error("Unable to call registry service", e);
        }

        log.debug("");
        log.debug("===============================================");
        log.debug("");

        return config != null ? config.getServerName() : "UNKNOWN";
    }

    public List<CameraInfoDto> syncCameras() throws ServiceException {
        List<CameraInfoDto> result;

        Map<String, CameraInfoDto> camerasMap = getCachable(true);
        result = Lists.newArrayList(camerasMap.values());

        return result;
    }

    public List<CameraInfoDto> listCameras() throws ServiceException {
        List<CameraInfoDto> result;

        Map<String, CameraInfoDto> camerasMap = getCachable(false);
        result = Lists.newArrayList(camerasMap.values());

        return result;
    }

    @Override
    public CameraDto getCameraDetails(CameraDto camera) {
        CameraInfoDto cameraDto = findById(camera.getExternalId());
        if (cameraDto != null) {
            if (cameraDto.getGroup() != null) {
                camera.setCameraGroup(new CameraGroupDto(
                        cameraDto.getGroup().getGroupId(),
                        cameraDto.getGroup().getName(),
                        cameraDto.getGroup().getDescription()
                ));
            }

            CameraSecurityInfo securityInfo = cameraDto.getInfo() != null ? cameraDto.getInfo().getCameraSecurity() : null;
            if (securityInfo != null) {
                camera.setLive(securityInfo.isLive());
            }
            ArrayOfStreamInfo streamInfo = cameraDto.getInfo() != null ? cameraDto.getInfo().getStreams() : null;
            List<StreamInfo> streams = streamInfo != null ? streamInfo.getStreamInfo() : null;
            if (!CollectionUtils.isEmpty(streams)) {
                List<CameraStreamDto> cameraStreams = Lists.newArrayList();
                streams.forEach(stream -> cameraStreams.add(new CameraStreamDto(
                        stream.getStreamId(),
                        stream.getName()
                )));
                camera.setStreams(cameraStreams);
            }
        }

        return camera;
    }

    @Nullable
    public CameraInfoDto findById(String cameraId) {
        CameraInfoDto cameraInfo = null;
        try {
            Map<String, CameraInfoDto> cameras = getCachable(false);
            cameraInfo = cameras.get(cameraId);
        } catch (ServiceException e) {
            log.error("Unable to load camera details: " + cameraId, e);
        }

        return cameraInfo;
    }

    private @NonNull Map<String, CameraInfoDto> getCachable(boolean updateCache) throws ServiceException {
        Map<String, CameraInfoDto> cameras = cameraCache.get();
        if (updateCache) {
            final Map<String, CameraInfoDto> camerasMap = listCamerasInternal();
            if (!CollectionUtils.isEmpty(camerasMap)) {
                if (cameras == null) {
                    cameras = Maps.newHashMap();
                }
                // Do not override but combine
                cameras.putAll(camerasMap);
                cameraCache.set(cameras);
            }
        }

        return cameras != null ? cameras : Collections.emptyMap();
    }

    protected Map<String, CameraInfoDto> listCamerasInternal() throws ServiceException {
        Map<String, CameraInfoDto> cameras = null;

        try {
            Map<String, CameraGroupInfo> groups = Maps.newHashMap();
            Map<String, CameraInfo> cameraInfoMap = Maps.newHashMap();
            Map<String, URI> recorderServiceMap = Maps.newHashMap();

            String token = getTokenService().currentToken();
            log.debug("MIP :: List cameras {} ...", token);
            ConfigurationInfo config = serverCommandService.getConfiguration(token);
            if (config != null) {
                ArrayOfCameraGroupInfo array = config.getCameraGroups();
                List<CameraGroupInfo> cameraGroupInfo = array != null ? array.getCameraGroupInfo() : null;
                if (!CollectionUtils.isEmpty(cameraGroupInfo)) {
                    for (CameraGroupInfo cameraGroup : cameraGroupInfo) {
                        ArrayOfguid camerasList = cameraGroup.getCameras();
                        List<String> cameraIds = camerasList != null ? camerasList.getGuid() : Collections.emptyList();
                        cameraIds.forEach(id -> groups.put(id, cameraGroup));
                    }
                }

                ArrayOfRecorderInfo arrayOfRecorderInfo = config.getRecorders();
                List<RecorderInfo> recorderServices = arrayOfRecorderInfo != null ? arrayOfRecorderInfo.getRecorderInfo() : null;
                if (!CollectionUtils.isEmpty(recorderServices)) {
                    for (RecorderInfo recorderService : recorderServices) {
                        log.info("MIP :: Found recording service {}", recorderService.getName());
                        URI imageServiceUri = null;
                        try {
                            imageServiceUri = new URI(recorderService.getWebServerUri());
                        } catch (URISyntaxException e) {
                            log.error("MIP :: Unable to parse Image Service connection {}", recorderService.getWebServerUri(), e);
                        }

                        ArrayOfCameraInfo camerasList = recorderService.getCameras();
                        List<CameraInfo> cameraInfos = camerasList != null ? camerasList.getCameraInfo() : null;
                        if (!CollectionUtils.isEmpty(cameraInfos)) {
                            for (CameraInfo camera : cameraInfos) {
                                cameraInfoMap.put(camera.getDeviceId(), camera);
                                recorderServiceMap.put(camera.getDeviceId(), imageServiceUri);
                            }
                        }
                    }
                }
            }

            if (cameraInfoMap.size() > 0) {
                Map<String, CameraDeviceStatus> cameraStatus = Maps.newHashMap();
                log.debug("MIP :: CAMERA Sync. Read current camera status. Total cameras: {}, batch size: {}", cameraInfoMap.size(), batchSize);
                List<String> cameraIds = Lists.newArrayList(cameraInfoMap.keySet());
                IntStream.range(0, (cameraIds.size() + batchSize - 1) / batchSize)
                        .mapToObj(i -> cameraIds.subList(i * batchSize, Math.min(cameraIds.size(), (i + 1) * batchSize)))
                        .forEach(batch -> {
                            try {
                                com.mip.recorderStatus.ArrayOfGuid idList = new com.mip.recorderStatus.ArrayOfGuid();
                                idList.getGuid().addAll(batch);

                                com.mip.recorderStatus.Status status = getRecorderStatusService().getCurrentDeviceStatus(getTokenService().currentToken(), idList);
                                if (status != null && status.getCameraDeviceStatusArray() != null && !CollectionUtils.isEmpty(status.getCameraDeviceStatusArray().getCameraDeviceStatus())) {
                                    status.getCameraDeviceStatusArray().getCameraDeviceStatus().forEach(deviceStatus -> cameraStatus.put(deviceStatus.getDeviceId(), deviceStatus));
                                }
                            } catch (Exception e) {
                                log.error("MIP :: CAMERA Sync :: BATCH Unable to receive immediate camera states from the RecorderStatusService. \n{}",
                                        StringUtils.join(batch, "\n"));
                            }
                        });

                // combine all camera information together
                cameras = Maps.newHashMap();
                for (Map.Entry<String, CameraInfo> cameraInfos : cameraInfoMap.entrySet()) {
                    CameraInfo camera = cameraInfos.getValue();
                    if (camera != null) {
                        CameraGroupInfo group = groups.get(cameraInfos.getKey());
                        URI imageServiceUri = recorderServiceMap.get(cameraInfos.getKey());
                        CameraDeviceStatus deviceStatus = cameraStatus.get(camera.getDeviceId());
                        cameras.put(cameraInfos.getKey(), new CameraInfoDto(camera.getDeviceId(), camera, group, deviceStatus, imageServiceUri));
                        log.debug("MIP :: CAMERA Sync. Camera: {}, Stream Url: {}", camera.getName(), imageServiceUri);
                    }
                }
            }
        } catch (Exception e) {
            if (e.getCause() != null && (e.getCause() instanceof NoRouteToHostException
                    || e.getCause() instanceof ConnectException
                    || e.getCause() instanceof HTTPException && ((HTTPException)e.getCause()).getResponseCode() == 503)) {
                log.error("MIP :: Unable to read cameras from MIP service. {}", e.getCause().getMessage());
            } else {
                log.error("MIP :: Unable to read cameras from MIP service", e);
            }
        }

        return cameras;
    }

    @Override
    public ICameraStream streamVideo(String cameraId, String streamId) throws ServiceException {

        String error;
        try {
            CameraInfoDto camera = findById(cameraId);
            if (camera == null) throw new UnableToConnectException("Unknown connection for camera with id: " + cameraId);

            final ImageServerConnection connection = new ImageServerConnection(cameraId, streamId, this);
            connection.connect(camera.getCameraServiceHost(), camera.getCameraServicePort(), camera.isCameraServiceSsl());

            ConnectCommand connectCommand = new ConnectCommand();
            connectCommand.setConnectParameters(cameraId, streamId, getTokenService().currentToken());
            connectCommand.setAlwaysStdJpeg(true);

            Transcode transcode = new Transcode();
            transcode.setAllFrames(true);
            connectCommand.setTranscode(transcode);

            ConnectResponse response = connection.sendCommand(connectCommand, ConnectResponse.class);
            if (response.isConnected()) {
                return new CameraStream(connection);
            }

            error = response.getErrorReason();
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unable to connect Image Server", e);
            throw new ServiceException("Unable to connect Image Server", e);
        }

        throw new UnableToConnectException(error != null ? error : "Unable to connect Image Server for the camera. Error: " + cameraId);
    }

    @Override
    public void onEvent(String cameraId, String streamId, MipCameraEventDto event) {

        try {
            List<IEvent<?>> events = Lists.newArrayList();

            if (event instanceof MipCameraStatusEventDto) {
                MipCameraStatusEventDto cameraStatusEvent = (MipCameraStatusEventDto) event;
                if (!CollectionUtils.isEmpty(cameraStatusEvent.getStatus())) {
                    events.add(new MipCameraDeviceEvent(cameraId).payload(cameraStatusEvent));
                }
            } else if (event instanceof MipCameraStreamingStoppedEventDto) {
                log.debug("Camera steaming stopped. Camera id: {}, camera stream: {}", cameraId, streamId);
            }
            else {
                events.add(new UnknownCameraEvent("cameraEvent", cameraId, getJsonMapper().writeValueAsString(event)));
            }

            for (IEvent<?> cameraEvent : events) {
                eventHub.post(cameraEvent);
            }

        } catch (ServiceException | JsonProcessingException e) {
            log.error("Unable to trigger an application event", e);
        }
    }

    private static class CameraStream implements ICameraStream {

        private final ImageServerConnection connection;

        public CameraStream(ImageServerConnection connection) {
            this.connection = connection;
        }

        @Override
        public void close() {
            connection.close();
        }

        @Override
        public InputStream stream() throws ImageServerException {
            return connection.startStreaming(true);
        }
    }

    @EventPath(path = UnknownCameraEvent.PATH)
    private static class UnknownCameraEvent extends BaseCameraEvent<StringPayload> {
        public static final String PATH = "unknown";

        public UnknownCameraEvent(String type, String cameraId, String data) {
            setDeviceId(cameraId);
            setPayload(new StringPayload(type, data));
        }

        @Override
        public String getEventPath() {
            return EventUtils.generatePath(UnknownCameraEvent.PATH, super.getEventPath());
        }
    }
}
