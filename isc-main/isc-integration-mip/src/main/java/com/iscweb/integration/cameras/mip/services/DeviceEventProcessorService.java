package com.iscweb.integration.cameras.mip.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.iscweb.common.events.BaseIntegrationRawEvent;
import com.iscweb.common.events.CommonEventTypes;
import com.iscweb.common.events.integration.DeviceEventConverterResult;
import com.iscweb.common.events.integration.camera.CameraStateType;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.IDeviceStateDto;
import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.model.dto.entity.core.CameraGroupDto;
import com.iscweb.common.model.dto.entity.core.CameraStreamDto;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.model.event.camera.CameraIncrementalUpdateEvent;
import com.iscweb.common.model.event.camera.CameraStateEvent;
import com.iscweb.common.model.metadata.CameraStatus;
import com.iscweb.common.model.metadata.CameraType;
import com.iscweb.common.service.integration.IDeviceEventProcessor;
import com.iscweb.integration.cameras.mip.dto.CameraInfoDto;
import com.iscweb.integration.cameras.mip.events.CameraSyncEvent;
import com.iscweb.integration.cameras.mip.events.MipCameraDeviceEvent;
import com.iscweb.integration.cameras.mip.services.streaming.dto.LiveStatusItem;
import com.iscweb.integration.cameras.mip.services.streaming.events.MipCameraStatusEventDto;
import com.iscweb.integration.cameras.mip.services.state.MipConnectionStateProducer;
import com.iscweb.integration.cameras.mip.services.state.MipHardwareStateProducer;
import com.iscweb.integration.cameras.mip.services.state.MipLiveStateProducer;
import com.iscweb.integration.cameras.mip.services.state.MipMotionStateProducer;
import com.iscweb.integration.cameras.mip.services.state.MipRecordingStateProducer;
import com.mip.command.ArrayOfStreamInfo;
import com.mip.command.CameraGroupInfo;
import com.mip.command.CameraSecurityInfo;
import com.mip.command.StreamInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Camera device event processor implementation.
 */
@Slf4j
@Service("CameraDeviceEventConverter")
public class DeviceEventProcessorService implements IDeviceEventProcessor {

    private final Map<CameraStateType, IStateProducer<DeviceStateItemDto, CameraDto, MipCameraStatusEventDto>> STATE_PROVIDERS = new HashMap<>() {{
        put(CameraStateType.CONNECTION, new MipConnectionStateProducer());
        put(CameraStateType.LIVE, new MipLiveStateProducer());
        put(CameraStateType.MOTION, new MipMotionStateProducer());
        put(CameraStateType.RECORDING, new MipRecordingStateProducer());
        put(CameraStateType.HARDWARE, new MipHardwareStateProducer());
        // CameraStateType.ALERTS
    }};
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ObjectMapper objectMapper;

    /**
     * @see IDeviceEventProcessor#isSupported(IEvent)
     */
    @Override
    public boolean isSupported(IEvent<ITypedPayload> event) {
        return event.getPayload() != null && (
                CommonEventTypes.CAMERA_SYNC.code().equals(event.getPayload().getType())
                        || MipCameraDeviceEvent.PATH.equals(event.getPayload().getType())
        );
    }

    /**
     * @see IDeviceEventProcessor#parseRawEvents(BaseIntegrationRawEvent)
     */
    @Override
    public <P extends ITypedPayload, R extends IEvent<P>> List<R> parseRawEvents(BaseIntegrationRawEvent<ITypedPayload> deviceEvent) {
        List<R> result = Collections.emptyList();

        // todo(dmorozov): Process raw camera events here if any
        // if (CommonEventTypes.CAMERA_STREAM.code().equals(deviceEvent.getPayload().getType())) {
        //     result = ... process
        // }

        return result;
    }

    /**
     * @see IDeviceEventProcessor#process(IEvent, IExternalEntityDto, IDeviceStateDto)
     */
    @Override
    public DeviceEventConverterResult process(IEvent<? extends ITypedPayload> event, IExternalEntityDto deviceDto, IDeviceStateDto deviceStateDto) {
        DeviceEventConverterResult result = null;

        if (CommonEventTypes.CAMERA_SYNC.code().equals(event.getPayload().getType())) {
            CameraSyncEvent cameraSyncEvent = (CameraSyncEvent) event;
            result = generateSyncEvent(cameraSyncEvent, deviceDto, deviceStateDto);
        } else if (MipCameraDeviceEvent.PATH.equals(event.getPayload().getType())) {
            result = generateStateEvent((MipCameraDeviceEvent) event, deviceDto, deviceStateDto);
        }

        return result;
    }

    private DeviceEventConverterResult generateSyncEvent(CameraSyncEvent event, IExternalEntityDto deviceDto, IDeviceStateDto deviceStateDto) {
        CameraInfoDto camera = event.getPayload().getData();

        CameraDto cameraDto = deviceDto != null ? (CameraDto) deviceDto : new CameraDto();
        cameraDto.setLastSyncTime(event.getEventTime());
        cameraDto.setExternalId(camera.getInfo().getDeviceId());
        cameraDto.setName(camera.getInfo().getName());
        cameraDto.setDescription(camera.getInfo().getDescription());
        cameraDto.setType(CameraType.VIDEO);
        cameraDto.setStatus(CameraStatus.ACTIVATED);

        CameraGroupInfo group = camera.getGroup();
        if (group != null) {
            cameraDto.setCameraGroup(new CameraGroupDto(
                    group.getGroupId(),
                    group.getName(),
                    group.getDescription()
            ));
        }

        CameraSecurityInfo securityInfo = camera.getInfo().getCameraSecurity();
        if (securityInfo != null) {
            cameraDto.setLive(securityInfo.isLive());
        }

        ArrayOfStreamInfo streamInfo = camera.getInfo().getStreams();
        List<StreamInfo> streams = streamInfo != null ? streamInfo.getStreamInfo() : null;
        if (!CollectionUtils.isEmpty(streams)) {
            List<CameraStreamDto> cameraStreams = Lists.newArrayList();
            streams.forEach(stream -> cameraStreams.add(new CameraStreamDto(
                    stream.getStreamId(),
                    stream.getName()
            )));
            cameraDto.setStreams(cameraStreams);
        }

        URI imageServiceUri = camera.getRecorderService();
        if (imageServiceUri != null) {
            cameraDto.setCameraServiceHost(imageServiceUri.getHost());
            cameraDto.setCameraServicePort(imageServiceUri.getPort());
            cameraDto.setCameraServiceSsl("HTTPS".equalsIgnoreCase(imageServiceUri.getScheme()));
        }

        MipCameraDeviceEvent deviceEvent = new MipCameraDeviceEvent(cameraDto.getId());
        MipCameraStatusEventDto payload = new MipCameraStatusEventDto();
        payload.setStatusTime(event.getEventTime().toInstant().toEpochMilli());
        payload.setStatus(List.of(LiveStatusItem.fromType(LiveStatusItem.StatusType.Sync)));
        deviceEvent.setEntityType(EntityType.CAMERA);
        deviceEvent.setPayload(payload);
        deviceEvent.setCorrelationId(event.getCorrelationId());
        deviceEvent.setReferenceId(event.getEventId());
        deviceEvent.setReceivedTime(event.getEventTime());
        DeviceEventConverterResult stateProcessingResult = generateStateEvent(deviceEvent, cameraDto, deviceStateDto);

        CameraIncrementalUpdateEvent syncEvent = new CameraIncrementalUpdateEvent(cameraDto.getEntityId());
        syncEvent.setEventId(deviceEvent.getEventId());
        syncEvent.setCorrelationId(deviceEvent.getCorrelationId());
        syncEvent.setReferenceId(event.getEventId());
        syncEvent.setEventTime(ZonedDateTime.now());
        syncEvent.setReceivedTime(deviceEvent.getEventTime());
        syncEvent.setPayload(new CameraIncrementalUpdateEvent.CameraDeviceUpdatePayload(
                cameraDto.getEntityId(),
                cameraDto,
                "SYNC",
                "Synchronization"));

        List<IEvent<? extends ITypedPayload>> events = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(stateProcessingResult.getEvents())) {
            // events.add(deviceEvent);
            events.add(syncEvent);
            events.addAll(stateProcessingResult.getEvents());
        } else if (deviceDto == null) {
            // events.add(deviceEvent);
            events.add(syncEvent);
        }

        CameraDto updatedCamera = stateProcessingResult.getUpdatedDevice() != null ? (CameraDto) stateProcessingResult.getUpdatedDevice() : cameraDto;
        IDeviceStateDto updatedState = stateProcessingResult.getUpdatedDeviceState() != null ? stateProcessingResult.getUpdatedDeviceState() : deviceStateDto;
        updatedCamera.setLastSyncTime(event.getEventTime());
        return new DeviceEventConverterResult(updatedCamera, updatedState, events);
    }

    private DeviceEventConverterResult generateStateEvent(MipCameraDeviceEvent deviceEvent, IExternalEntityDto deviceDto, IDeviceStateDto deviceStateDto) {
        if (deviceDto != null) {
            Map<CameraStateType, DeviceStateItemDto> state = deviceStateDto.getState() != null ? deviceStateDto.getState()
                    .stream()
                    .collect(Collectors.toMap(e -> CameraStateType.valueOf(e.getType()), e -> e)) : Maps.newHashMap();
            Map<CameraStateType, DeviceStateItemDto> newState = generateNewState((CameraDto) deviceDto, state, deviceEvent);

            Set<DeviceStateItemDto> updatedState = Sets.newHashSet(newState.values());
            List<IEvent<? extends ITypedPayload>> events = Lists.newArrayList();
            if (!Objects.equals(state, newState)) {
                CameraStateEvent evt = new CameraStateEvent(deviceDto.getEntityId(), updatedState);
                evt.setCorrelationId(deviceEvent.getCorrelationId());
                evt.setReferenceId(deviceEvent.getEventId());
                evt.setEventTime(ZonedDateTime.now());
                evt.setReceivedTime(deviceEvent.getEventTime());
                events.add(evt);

                CameraIncrementalUpdateEvent evt2 = new CameraIncrementalUpdateEvent(deviceDto.getEntityId());
                evt2.setCorrelationId(deviceEvent.getCorrelationId());
                evt2.setReferenceId(evt.getEventId());
                evt2.setEventTime(ZonedDateTime.now());
                evt2.setReceivedTime(deviceEvent.getEventTime());
                evt2.setPayload(new CameraIncrementalUpdateEvent.CameraDeviceUpdatePayload(
                        deviceDto.getEntityId(),
                        (CameraDto) deviceDto,
                        deviceEvent.getPayload().getStatus().stream().map(status -> status.statusType().name()).collect(Collectors.joining(" / ")),
                        deviceEvent.getPayload().getStatus().stream().map(status -> status.statusType().getDescription()).collect(Collectors.joining(". "))));
                events.add(evt2);
            }

            ((CameraDto) deviceDto).setLastSyncTime(deviceEvent.getEventTime());
            return new DeviceEventConverterResult(deviceDto,
                    new CameraStateEvent.CameraDeviceStatePayload(deviceDto.getEntityId(), updatedState),
                    events);
        }

        return new DeviceEventConverterResult(null, null, Collections.emptyList());
    }

    public Map<CameraStateType, DeviceStateItemDto> generateNewState(CameraDto camera, Map<CameraStateType, DeviceStateItemDto> state, MipCameraDeviceEvent deviceEvent) {
        Map<CameraStateType, DeviceStateItemDto> newState = Maps.newHashMap(state);

        for (Map.Entry<CameraStateType, IStateProducer<DeviceStateItemDto, CameraDto, MipCameraStatusEventDto>> entry : STATE_PROVIDERS.entrySet()) {
            DeviceStateItemDto oldState = newState.get(entry.getKey());
            DeviceStateItemDto updatedState = entry.getValue().process(oldState, camera, deviceEvent.getPayload());
            if (updatedState != null) {
                newState.put(entry.getKey(), updatedState);
            } else {
                newState.remove(entry.getKey());
            }
        }

        DeviceStateItemDto stateItem = newState.get(CameraStateType.CONNECTION);
        if (stateItem == null) {
            if (newState.get(CameraStateType.LIVE) != null
                    || newState.get(CameraStateType.MOTION) != null
                    || newState.get(CameraStateType.RECORDING) != null) {
                newState.put(CameraStateType.CONNECTION, new DeviceStateItemDto(CameraStateType.CONNECTION.name(), LiveStatusItem.StatusType.ConnectionRestored.name(), ZonedDateTime.now()));
            }
        } else if (LiveStatusItem.StatusType.ConnectionLost.name().equalsIgnoreCase(stateItem.getValue())) {
            newState.remove(CameraStateType.LIVE);
            newState.remove(CameraStateType.MOTION);
            newState.remove(CameraStateType.RECORDING);
        }

        return newState;
    }
}
