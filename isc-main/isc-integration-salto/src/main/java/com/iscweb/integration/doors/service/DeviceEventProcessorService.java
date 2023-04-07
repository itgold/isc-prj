package com.iscweb.integration.doors.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseIntegrationRawEvent;
import com.iscweb.common.events.CommonEventTypes;
import com.iscweb.common.events.integration.DeviceEventConverterResult;
import com.iscweb.common.events.integration.door.DoorStateType;
import com.iscweb.common.events.integration.door.DoorUpdateEvent;
import com.iscweb.common.events.payload.StringPayload;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.IDeviceStateDto;
import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.model.event.door.DoorIncrementalUpdateEvent;
import com.iscweb.common.model.event.door.DoorStateEvent;
import com.iscweb.common.model.metadata.DoorBatteryStatus;
import com.iscweb.common.model.metadata.DoorConnectionStatus;
import com.iscweb.common.model.metadata.DoorOnlineStatus;
import com.iscweb.common.model.metadata.DoorOpeningMode;
import com.iscweb.common.model.metadata.DoorTamperStatus;
import com.iscweb.common.service.IDeviceAlertService;
import com.iscweb.common.service.integration.IDeviceEventProcessor;
import com.iscweb.common.util.EventUtils;
import com.iscweb.integration.doors.events.DoorSyncEvent;
import com.iscweb.integration.doors.events.DoorSyncPayload;
import com.iscweb.integration.doors.events.SaltoDoorDeviceEvent;
import com.iscweb.integration.doors.events.SaltoEventTypes;
import com.iscweb.integration.doors.events.SaltoRawEvent;
import com.iscweb.integration.doors.models.SaltoStreamEventDto;
import com.iscweb.integration.doors.models.doors.OnlineDoorStatusDto;
import com.iscweb.integration.doors.models.enums.Operation;
import com.iscweb.integration.doors.service.state.AlertsStateProducer;
import com.iscweb.integration.doors.service.state.SaltoBatteryStateProducer;
import com.iscweb.integration.doors.service.state.SaltoConnectionStateProducer;
import com.iscweb.integration.doors.service.state.SaltoHardwareStateProducer;
import com.iscweb.integration.doors.service.state.SaltoIntrusionStateProducer;
import com.iscweb.integration.doors.service.state.SaltoModeStateProducer;
import com.iscweb.integration.doors.service.state.SaltoRejectionStateProducer;
import com.iscweb.integration.doors.service.state.SaltoStatusStateProducer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Door device event processor implementation.
 */
@Slf4j
@Service("DoorDeviceEventConverter")
public class DeviceEventProcessorService implements IDeviceEventProcessor {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ObjectMapper objectMapper;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IDeviceAlertService alertService;

    @Getter
    private final SaltoStatusStateProducer statusProvider = new SaltoStatusStateProducer();

    @Getter
    private final Map<DoorStateType, IStateProducer<DeviceStateItemDto, DoorDto, SaltoStreamEventDto>> stateProviders =
            new HashMap<>() {{
                put(DoorStateType.COMMUNICATION, new SaltoConnectionStateProducer());
                put(DoorStateType.HARDWARE, new SaltoHardwareStateProducer());
                put(DoorStateType.MODE, new SaltoModeStateProducer());
                put(DoorStateType.REJECTION, new SaltoRejectionStateProducer());
                put(DoorStateType.STATUS, statusProvider);
                put(DoorStateType.INTRUSION, new SaltoIntrusionStateProducer());
                put(DoorStateType.BATTERY, new SaltoBatteryStateProducer());
                put(DoorStateType.ALERTS, new AlertsStateProducer(alertService));
            }};

    /**
     * @see IDeviceEventProcessor#isSupported(IEvent)
     */
    @Override
    public boolean isSupported(IEvent<ITypedPayload> event) {
        return event.getPayload() != null && (
                SaltoRawEvent.PATH.equals(event.getPayload().getType()) ||
                CommonEventTypes.DOOR_SYNC.code().equals(event.getPayload().getType()) ||
                SaltoEventTypes.EVENT_STREAM.code().equals(event.getPayload().getType())
        );
    }

    /**
     * @see IDeviceEventProcessor#parseRawEvents(BaseIntegrationRawEvent)
     */
    @Override
    public <P extends ITypedPayload, R extends IEvent<P>> List<R> parseRawEvents(BaseIntegrationRawEvent<ITypedPayload> deviceEvent) throws ServiceException {
        List<R> result = Collections.emptyList();

        if (SaltoRawEvent.PATH.equals(deviceEvent.getPayload().getType())) {
            CollectionType listItemClass = getObjectMapper().getTypeFactory().constructCollectionType(List.class, SaltoStreamEventDto.class);
            String payload = ((StringPayload) deviceEvent.getPayload()).getValue();
            try {
                List<SaltoStreamEventDto> events = getObjectMapper().readValue(payload, listItemClass);
                result = events.stream().map(event -> {
                    SaltoDoorDeviceEvent e = new SaltoDoorDeviceEvent();
                    e.setExternalEntityId(event.getDoorExtId());
                    e.setEntityType(EntityType.DOOR);
                    e.setPayload(event);
                    e.setReferenceId(deviceEvent.getEventId());
                    e.setReceivedTime(deviceEvent.getEventTime());
                    e.setCorrelationId(deviceEvent.getCorrelationId());
                    e.setEventTime(deviceEvent.getEventTime());
                    //noinspection unchecked
                    return (R) e;
                }).collect(Collectors.toList());
            } catch (JsonProcessingException e) {
                log.error("Unable to parse raw Salto payload: {}", payload, e);
                throw new ServiceException("Unable to parse raw Salto payload", e);
            }
        }

        return result;
    }

    /**
     * @see IDeviceEventProcessor#process(IEvent, IExternalEntityDto, IDeviceStateDto)
     */
    @Override
    public DeviceEventConverterResult process(IEvent<? extends ITypedPayload> event, IExternalEntityDto deviceDto, IDeviceStateDto deviceStateDto) {
        DeviceEventConverterResult result = null;

        // todo(dmorozov): FIX IT the sync event can be state event as well !!! Example: mode change
        if (CommonEventTypes.DOOR_SYNC.code().equals(event.getPayload().getType())) {
            try { log.error("!!! SYNC !!! {}", getObjectMapper().writeValueAsString(event)); } catch (Exception e) {}
            result = generateSyncEvent((DoorSyncEvent) event, deviceDto, deviceStateDto);
        } else if (SaltoEventTypes.EVENT_STREAM.code().equals(event.getPayload().getType())) {
            try { log.error("!!! DEVICE !!! {}", getObjectMapper().writeValueAsString(event)); } catch (Exception e) {}
            SaltoDoorDeviceEvent deviceEvent = (SaltoDoorDeviceEvent) event;
            if (isStateEvent(deviceEvent.getPayload())) {
                result = generateStateEvent(deviceEvent, deviceDto, deviceStateDto);
            } else {
                // todo(dmorozov): what to do in that case?
                DoorUpdateUnknownEvent evt = new DoorUpdateUnknownEvent(deviceEvent.getExternalEntityId());
                evt.setCorrelationId(event.getCorrelationId());
                evt.setReferenceId(event.getEventId());
                evt.setPayload(deviceEvent.getPayload());
                evt.setEventTime(ZonedDateTime.now());
                evt.setReceivedTime(deviceEvent.getEventTime());
                result = new DeviceEventConverterResult(null, null, Collections.singletonList(evt));
            }

//            if (isNeedSync(deviceEvent.getPayload())) {
//                // todo(dmorozov): trigger sync process to get full door state
//                // result.getEvents().add(new EnforceSyncEvent(deviceDto));
//            }
        }

        return result;
    }

    private DeviceEventConverterResult generateStateEvent(SaltoDoorDeviceEvent deviceEvent,
                                                          IExternalEntityDto deviceDto,
                                                          IDeviceStateDto deviceStateDto) {

        DeviceEventConverterResult result;

        Map<DoorStateType, DeviceStateItemDto> state = deviceStateDto.getState() != null ? deviceStateDto.getState()
                                                                                                       .stream()
                                                                                                       .collect(Collectors.toMap(e -> DoorStateType.valueOf(e.getType()), e -> e)) : Maps.newHashMap();

        Map<DoorStateType, DeviceStateItemDto> newState = generateNewState((DoorDto) deviceDto, state, deviceEvent);
        if (deviceDto != null) {
            List<IEvent<? extends ITypedPayload>> events = Lists.newArrayList();

            // Important: if the state didn't change we're still going to update it in
            // the DB (to have updated time) but do not send state event.

            Set<DeviceStateItemDto> updatedState = Sets.newHashSet(newState.values());
            if (!Objects.equals(state, newState)) {
                DoorStateEvent evt = new DoorStateEvent(deviceDto.getEntityId(), updatedState);
                evt.setCorrelationId(deviceEvent.getCorrelationId());
                evt.setReferenceId(deviceEvent.getEventId());
                evt.setEventTime(ZonedDateTime.now());
                evt.setReceivedTime(deviceEvent.getEventTime());
                events.add(evt);

                DoorIncrementalUpdateEvent evt2 = new DoorIncrementalUpdateEvent(deviceDto.getEntityId());
                evt2.setCorrelationId(deviceEvent.getCorrelationId());
                evt2.setReferenceId(deviceEvent.getEventId());
                evt2.setEventTime(ZonedDateTime.now());
                evt2.setReceivedTime(deviceEvent.getEventTime());
                evt2.setPayload(new DoorIncrementalUpdateEvent.DoorDeviceUpdatePayload(
                        deviceDto.getEntityId(),
                        (DoorDto) deviceDto,
                        deviceEvent.getPayload().getOperation().name(),
                        deviceEvent.getPayload().getOperation().getMessage()));
                events.add(evt2);

                ((DoorDto) deviceDto).setLastSyncTime(deviceEvent.getEventTime());
                result = new DeviceEventConverterResult(deviceDto,
                        new DoorStateEvent.DoorDeviceStatePayload(deviceDto.getEntityId(), updatedState),
                        events);
            } else {
                result = new DeviceEventConverterResult(null, null, Lists.newArrayList());
            }
        } else {
            result = new DeviceEventConverterResult(null, null, Lists.newArrayList());
        }

        return result;
    }

    private DeviceEventConverterResult generateSyncEvent(DoorSyncEvent event, IExternalEntityDto deviceDto, IDeviceStateDto deviceStateDto) {
        DoorSyncPayload doorPayload = event.getPayload();
        DoorDto door = deviceDto != null ? (DoorDto) deviceDto : new DoorDto();
        OnlineDoorStatusDto status = doorPayload.getStatus();

        door.setLastSyncTime(event.getEventTime());
        door.setExternalId(doorPayload.getData().getId());
        door.setName(doorPayload.getData().getName());
        door.setDescription(doorPayload.getData().getDescription());
        door.setConnectionStatus(status.getCommStatus() != null ? status.getCommStatus().toDto() : DoorConnectionStatus.UNKNOWN);
        door.setOnlineStatus(status.getDoorStatus() != null ? status.getDoorStatus().toDto() : DoorOnlineStatus.UNKNOWN);
        door.setBatteryStatus(status.getBatteryStatus() != null ? status.getBatteryStatus().toDto() : DoorBatteryStatus.UNKNOWN);
        door.setTamperStatus(status.getTamperStatus() != null ? status.getTamperStatus().toDto() : DoorTamperStatus.UNKNOWN);
        door.setOpeningMode(doorPayload.getData().getOpeningMode() != null ? doorPayload.getData().getOpeningMode().toDto() : DoorOpeningMode.STANDARD);
        door.setBatteryLevel(doorPayload.getData().getBatteryStatus());
        door.setUpdateRequired(doorPayload.getData().getUpdateRequired());

        // Process sync event as a state event
        SaltoDoorDeviceEvent deviceEvent = new SaltoDoorDeviceEvent();
        deviceEvent.setEventId(event.getEventId());
        deviceEvent.setExternalEntityId(event.getExternalEntityId());
        deviceEvent.setCorrelationId(event.getCorrelationId());
        deviceEvent.setReferenceId(event.getReferenceId());
        deviceEvent.setReceivedTime(event.getEventTime());
        SaltoStreamEventDto payload = new SaltoStreamEventDto();
        payload.setEventTime(Date.from(event.getEventTime().toInstant()));
        payload.setEventDateTime(Date.from(event.getEventTime().toInstant()));
        payload.setOperation(Operation.SYNC);
        deviceEvent.setPayload(payload);

        DeviceEventConverterResult stateProcessingResult = generateStateEvent(deviceEvent, door, deviceStateDto);

        List<IEvent<? extends ITypedPayload>> events = Lists.newArrayList();
        // If any events generated they were triggered by the SYNC event, so we need to add SYNC event as well
        if (!CollectionUtils.isEmpty(stateProcessingResult.getEvents())) {
            events.addAll(stateProcessingResult.getEvents());
        } else if (deviceDto == null) {
            deviceEvent.setEventId(UUID.randomUUID().toString());
            deviceEvent.setReferenceId(event.getEventId());
            events.add(deviceEvent);
        }

        DoorDto updatedDoor = stateProcessingResult.getUpdatedDevice() != null ? (DoorDto) stateProcessingResult.getUpdatedDevice() : door;
        IDeviceStateDto updatedState = stateProcessingResult.getUpdatedDeviceState() != null ? stateProcessingResult.getUpdatedDeviceState() : deviceStateDto;

        // hide some details from Dto we do not want to be in the event
        updatedDoor.setState(Sets.newHashSet());
        updatedDoor.setLastSyncTime(event.getEventTime());

        return new DeviceEventConverterResult(updatedDoor, updatedState, events);
    }

    protected boolean isNeedSync(SaltoStreamEventDto eventDto) {
        final List<Operation> stateUpdateEvents = Lists.newArrayList(Operation.AUTOMATIC_CHANGE);

        return stateUpdateEvents.contains(eventDto.getOperation());
    }

    public Map<DoorStateType, DeviceStateItemDto> generateNewState(DoorDto door, Map<DoorStateType, DeviceStateItemDto> state, SaltoDoorDeviceEvent deviceEvent) {
        Map<DoorStateType, DeviceStateItemDto> newState = Maps.newHashMap(state);

        for (Map.Entry<DoorStateType, IStateProducer<DeviceStateItemDto, DoorDto, SaltoStreamEventDto>> entry : getStateProviders().entrySet()) {
            DeviceStateItemDto oldState = newState.get(entry.getKey());
            DeviceStateItemDto updatedState = entry.getValue().process(oldState, door, deviceEvent.getPayload());
            if (updatedState != null) {
                newState.put(entry.getKey(), updatedState);
            } else {
                newState.remove(entry.getKey());
            }
        }

        // did we get open/close door event? If yes -> reset intrusion state
        if (getStatusProvider().isOpenCloseEvent(deviceEvent.getPayload())) {
            newState.remove(DoorStateType.INTRUSION);
            newState.remove(DoorStateType.REJECTION);
        }

        return newState;
    }

    protected boolean isStateEvent(SaltoStreamEventDto eventDto) {
        boolean result = false;
        /*
          - Mode (office, standard, etc)
          - Battery (out of battery)
          - Connection status - Online/Offline
          - Rejections flag
          - Last Opened time
          - Tempered
          - Door left open (didn't closed on time)
          - Suspicious activity / Hack try: PPD connection etc
          - Hardware issue / change (119, 116)
         */
        for (Map.Entry<DoorStateType, IStateProducer<DeviceStateItemDto, DoorDto, SaltoStreamEventDto>> entry : stateProviders.entrySet()) {
            if (entry.getValue().hasStateData(eventDto)) {
                result = true;
            }
        }

        return result;
    }

    @NoArgsConstructor
    @EventPath(path = DoorUpdateUnknownEvent.PATH)
    public static class DoorUpdateUnknownEvent extends DoorUpdateEvent<SaltoStreamEventDto> {
        public static final String PATH = "unknown";

        public DoorUpdateUnknownEvent(String doorId) {
            super(doorId);
        }

        @Override
        public String getEventPath() {
            return EventUtils.generatePath(DoorUpdateUnknownEvent.PATH, super.getEventPath());
        }
    }
}
