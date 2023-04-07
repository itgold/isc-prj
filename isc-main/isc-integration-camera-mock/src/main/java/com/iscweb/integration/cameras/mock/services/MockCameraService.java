package com.iscweb.integration.cameras.mock.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.payload.StringPayload;
import com.iscweb.common.exception.ImageServerException;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.model.dto.entity.core.CameraGroupDto;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.camera.BaseCameraEvent;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.integration.camera.ICameraService;
import com.iscweb.common.service.integration.camera.ICameraStream;
import com.iscweb.common.util.EventUtils;
import com.iscweb.integration.cameras.mock.dto.CameraInfoDto;
import com.iscweb.integration.cameras.mock.events.MipCameraDeviceEvent;
import com.iscweb.integration.cameras.mock.services.streaming.CameraEventListener;
import com.iscweb.integration.cameras.mock.services.streaming.ImageServerConnection;
import com.iscweb.integration.cameras.mock.services.streaming.events.MipCameraStreamingStoppedEventDto;
import com.iscweb.integration.cameras.mock.services.streaming.events.MipCameraEventDto;
import com.iscweb.integration.cameras.mock.services.streaming.events.MipCameraStatusEventDto;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service("MockCameraService")
public class MockCameraService implements ICameraService, CameraEventListener<MipCameraEventDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IEventHub eventHub;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ObjectMapper jsonMapper;

    @Getter
    @Setter(onMethod = @__({@Value("${cameras.active.events.batchSize:#{100}}")}))
    private int batchSize;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private MockCamerasConfig camerasConfig;

    private final AtomicReference<Map<String, CameraInfoDto>> cameraCache = new AtomicReference<>(null);

    private final static CameraGroupDto cameraGroup = new CameraGroupDto("test_camera_group", "Mock Cameras Group", "Mock Cameras Group");

    @Override
    public String serviceInfo() {
        return "MIP_TEST";
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

            camera.setLive(true);
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

    protected Map<String, CameraInfoDto> listCamerasInternal() {
        Map<String, CameraInfoDto> cameras = Maps.newHashMap();
        getCamerasConfig().getCameras().forEach(cameraConfig -> {
            cameras.put(cameraConfig.getId(), new CameraInfoDto(cameraConfig.getId(),
                    cameraGroup,
                    cameraConfig.getName(),
                    cameraConfig.getDescription(),
                    cameraConfig.getVideo()));
        });

        return cameras;
    }

    @Override
    public ICameraStream streamVideo(String cameraId, String streamId) throws ServiceException {
        try {
            String videoPath = getCamerasConfig().getCameras().stream()
                    .filter(camera -> cameraId.equalsIgnoreCase(camera.getId()))
                    .findFirst()
                    .map(camera -> camera.getVideo())
                    .orElse(null);
            final ImageServerConnection connection = new ImageServerConnection(cameraId, videoPath, this);
            return new CameraStream(connection);
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void onEvent(String cameraId, MipCameraEventDto event) {

        try {
            List<IEvent<?>> events = Lists.newArrayList();

            if (event instanceof MipCameraStatusEventDto) {
                MipCameraStatusEventDto cameraStatusEvent = (MipCameraStatusEventDto) event;
                if (!CollectionUtils.isEmpty(cameraStatusEvent.getStatus())) {
                    events.add(new MipCameraDeviceEvent(cameraId).payload(cameraStatusEvent));
                }
            } else if (event instanceof MipCameraStreamingStoppedEventDto) {
                log.debug("Camera steaming stopped. Camera id: {}", cameraId);
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
            return connection.startStreaming();
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
