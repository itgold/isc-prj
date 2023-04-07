package com.iscweb.integration.radios.service;

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
import com.iscweb.common.events.integration.OnDemandDeviceSyncEvent;
import com.iscweb.common.events.integration.radio.RadioStateType;
import com.iscweb.common.events.integration.radio.RadioUpdateEvent;
import com.iscweb.common.events.payload.StringPayload;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.IDeviceStateDto;
import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.RadioDto;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.model.event.radio.RadioIncrementalUpdateEvent;
import com.iscweb.common.model.event.radio.RadioStateEvent;
import com.iscweb.common.security.ApplicationSecurity;
import com.iscweb.common.service.IDeviceAlertService;
import com.iscweb.common.service.integration.IDeviceEventProcessor;
import com.iscweb.common.util.EventUtils;
import com.iscweb.integration.radios.events.RadioSyncEvent;
import com.iscweb.integration.radios.events.RadioSyncPayload;
import com.iscweb.integration.radios.events.TrboNetEventTypes;
import com.iscweb.integration.radios.events.TrboNetRadioDeviceEvent;
import com.iscweb.integration.radios.events.TrboNetRawEvent;
import com.iscweb.integration.radios.events.TrboNetStreamEventDto;
import com.iscweb.integration.radios.models.TrboNetDeviceUpdateType;
import com.iscweb.integration.radios.service.state.AlertsStateProducer;
import com.iscweb.integration.radios.service.state.RadioConnectionStateProducer;
import com.iscweb.integration.radios.service.state.RadioHardwareStateProducer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Radio device event processor implementation.
 */
@Slf4j
@Service("TrboNetDeviceEventConverter")
public class DeviceEventProcessorService implements IDeviceEventProcessor {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ObjectMapper objectMapper;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IDeviceAlertService alertService;

    @Getter
    private final Map<RadioStateType, IStateProducer<DeviceStateItemDto, RadioDto, TrboNetStreamEventDto>> stateProviders =
            new HashMap<>() {{
                put(RadioStateType.COMMUNICATION, new RadioConnectionStateProducer());
                put(RadioStateType.HARDWARE, new RadioHardwareStateProducer());
                put(RadioStateType.ALERTS, new AlertsStateProducer(alertService));
            }};

    /**
     * @see IDeviceEventProcessor#isSupported(IEvent)
     */
    @Override
    public boolean isSupported(IEvent<ITypedPayload> event) {
        return event.getPayload() != null && (
                TrboNetRawEvent.PATH.equals(event.getPayload().getType()) ||
                        CommonEventTypes.RADIO_SYNC.code().equals(event.getPayload().getType()) ||
                        TrboNetEventTypes.EVENT_STREAM.code().equals(event.getPayload().getType())
        );
    }

    /**
     * @see IDeviceEventProcessor#parseRawEvents(BaseIntegrationRawEvent)
     */
    @Override
    public <P extends ITypedPayload, R extends IEvent<P>> List<R> parseRawEvents(BaseIntegrationRawEvent<ITypedPayload> deviceEvent) throws ServiceException {
        List<R> result = Collections.emptyList();

        if (TrboNetRawEvent.PATH.equals(deviceEvent.getPayload().getType())) {
            CollectionType listItemClass = getObjectMapper().getTypeFactory().constructCollectionType(List.class, TrboNetStreamEventDto.class);
            String payload = ((StringPayload) deviceEvent.getPayload()).getValue();
            try {
                List<TrboNetStreamEventDto> events = getObjectMapper().readValue(payload, listItemClass);
                result = events.stream().map(event -> {
                    TrboNetRadioDeviceEvent e = new TrboNetRadioDeviceEvent();
                    if (event.getDevice() != null) {
                        e.setExternalEntityId(String.valueOf(event.getDevice().getId()));
                    }

                    e.setEntityType(EntityType.RADIO);
                    e.setPayload(event);
                    e.setReferenceId(deviceEvent.getEventId());
                    e.setReceivedTime(deviceEvent.getEventTime());
                    e.setCorrelationId(deviceEvent.getCorrelationId());
                    e.setEventTime(deviceEvent.getEventTime());
                    //noinspection unchecked
                    return (R) e;
                }).collect(Collectors.toList());
            } catch (JsonProcessingException e) {
                log.error("Unable to parse raw TRBOnet payload: {}", payload, e);
                throw new ServiceException("Unable to parse raw TRBOnet payload", e);
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
        if (CommonEventTypes.RADIO_SYNC.code().equals(event.getPayload().getType())) {
            try { log.error("!!! SYNC !!! {}", getObjectMapper().writeValueAsString(event)); } catch (Exception e) {}
            result = generateSyncEvent((RadioSyncEvent) event, deviceDto, deviceStateDto);
        } else if (TrboNetEventTypes.EVENT_STREAM.code().equals(event.getPayload().getType())) {
            try { log.error("!!! DEVICE !!! {}", getObjectMapper().writeValueAsString(event)); } catch (Exception e) {}
            TrboNetRadioDeviceEvent deviceEvent = (TrboNetRadioDeviceEvent) event;
            if (isStateEvent(deviceEvent.getPayload())) {
                result = generateStateEvent(deviceEvent, deviceDto, deviceStateDto);
            } else {
                if (isNeedSync(deviceEvent.getPayload())) {
                    // trigger sync for radios
                    result = new DeviceEventConverterResult(null,
                            null,
                            Lists.newArrayList(new OnDemandDeviceSyncEvent(ApplicationSecurity.SYSTEM_USER,
                                    Lists.newArrayList(EntityType.RADIO),
                                    event.getEventId())));
                } else {
                    // todo(dmorozov): what to do in that case?
                    RadioUpdateUnknownEvent evt = new RadioUpdateUnknownEvent(deviceEvent.getExternalEntityId());
                    evt.setCorrelationId(event.getCorrelationId());
                    evt.setReferenceId(event.getEventId());
                    evt.setPayload(deviceEvent.getPayload());
                    evt.setEventTime(ZonedDateTime.now());
                    evt.setReceivedTime(deviceEvent.getEventTime());
                    result = new DeviceEventConverterResult(null, null, Collections.singletonList(evt));
                }
            }
        }

        return result;
    }

    private DeviceEventConverterResult generateStateEvent(TrboNetRadioDeviceEvent deviceEvent,
                                                          IExternalEntityDto deviceDto,
                                                          IDeviceStateDto deviceStateDto) {

        DeviceEventConverterResult result;

        Map<RadioStateType, DeviceStateItemDto> state = deviceStateDto.getState() != null ? deviceStateDto.getState()
                                                                                                       .stream()
                                                                                                       .collect(Collectors.toMap(e -> RadioStateType.valueOf(e.getType()), e -> e)) : Maps.newHashMap();

        Map<RadioStateType, DeviceStateItemDto> newState = generateNewState((RadioDto) deviceDto, state, deviceEvent);
        if (deviceDto != null) {
            List<IEvent<? extends ITypedPayload>> events = Lists.newArrayList();

            // Important: if the state didn't change we're still going to update it in
            // the DB (to have updated time) but do not send state event.

            Set<DeviceStateItemDto> updatedState = Sets.newHashSet(newState.values());
            if (!Objects.equals(state, newState)) {
                RadioStateEvent evt = new RadioStateEvent(deviceDto.getEntityId(), updatedState);
                evt.setCorrelationId(deviceEvent.getCorrelationId());
                evt.setReferenceId(deviceEvent.getEventId());
                evt.setEventTime(ZonedDateTime.now());
                evt.setReceivedTime(deviceEvent.getEventTime());
                events.add(evt);

                RadioIncrementalUpdateEvent evt2 = new RadioIncrementalUpdateEvent(deviceDto.getEntityId());
                evt2.setCorrelationId(deviceEvent.getCorrelationId());
                evt2.setReferenceId(deviceEvent.getEventId());
                evt2.setEventTime(ZonedDateTime.now());
                evt2.setReceivedTime(deviceEvent.getEventTime());
                evt2.setPayload(new RadioIncrementalUpdateEvent.RadioDeviceUpdatePayload(
                        deviceDto.getEntityId(),
                        (RadioDto) deviceDto,
                        deviceEvent.getPayload().getUpdateType().name(),
                        deviceEvent.getPayload().getUpdateType().getDescription()));
                events.add(evt2);

                ((RadioDto) deviceDto).setLastSyncTime(deviceEvent.getEventTime());
                result = new DeviceEventConverterResult(deviceDto,
                        new RadioStateEvent.RadioDeviceStatePayload(deviceDto.getEntityId(), updatedState),
                        events);
            } else {
                result = new DeviceEventConverterResult(null, null, Lists.newArrayList());
            }
        } else {
            result = new DeviceEventConverterResult(null, null, Lists.newArrayList());
        }

        return result;
    }

    private DeviceEventConverterResult generateSyncEvent(RadioSyncEvent event, IExternalEntityDto deviceDto, IDeviceStateDto deviceStateDto) {
        RadioSyncPayload radioPayload = event.getPayload();
        RadioDto radioDto = radioPayload.toRadioDto(deviceDto);
        radioDto.setLastSyncTime(event.getEventTime());

        // Process sync event as a state event
        TrboNetRadioDeviceEvent deviceEvent = new TrboNetRadioDeviceEvent();
        deviceEvent.setEventId(event.getEventId());
        deviceEvent.setExternalEntityId(event.getExternalEntityId());
        deviceEvent.setCorrelationId(event.getCorrelationId());
        deviceEvent.setReferenceId(event.getReferenceId());
        deviceEvent.setReceivedTime(event.getEventTime());
        TrboNetStreamEventDto payload = new TrboNetStreamEventDto();
        payload.setEventTime(event.getEventTime());
        payload.setUpdateType(TrboNetDeviceUpdateType.SYNC);
        deviceEvent.setPayload(payload);

        DeviceEventConverterResult stateProcessingResult = generateStateEvent(deviceEvent, radioDto, deviceStateDto);

        List<IEvent<? extends ITypedPayload>> events = Lists.newArrayList();
        // If any events generated they were triggered by the SYNC event, so we need to add SYNC event as well
        if (!CollectionUtils.isEmpty(stateProcessingResult.getEvents())) {
            events.addAll(stateProcessingResult.getEvents());
        } else if (deviceDto == null) {
            deviceEvent.setEventId(UUID.randomUUID().toString());
            deviceEvent.setReferenceId(event.getEventId());
            events.add(deviceEvent);
        }

        RadioDto updatedRadio = stateProcessingResult.getUpdatedDevice() != null ? (RadioDto) stateProcessingResult.getUpdatedDevice() : radioDto;
        IDeviceStateDto updatedState = stateProcessingResult.getUpdatedDeviceState() != null ? stateProcessingResult.getUpdatedDeviceState() : deviceStateDto;

        // hide some details from Dto we do not want to be in the event
        updatedRadio.setState(Sets.newHashSet());
        updatedRadio.setLastSyncTime(event.getEventTime());

        return new DeviceEventConverterResult(updatedRadio, updatedState, events);
    }

    protected boolean isNeedSync(TrboNetStreamEventDto eventDto) {
        return eventDto.getUpdateType() == TrboNetDeviceUpdateType.BULK_UPDATE || eventDto.getUpdateType() == TrboNetDeviceUpdateType.RESTART;
    }

    public Map<RadioStateType, DeviceStateItemDto> generateNewState(RadioDto radio, Map<RadioStateType, DeviceStateItemDto> state, TrboNetRadioDeviceEvent deviceEvent) {
        Map<RadioStateType, DeviceStateItemDto> newState = Maps.newHashMap(state);

        for (Map.Entry<RadioStateType, IStateProducer<DeviceStateItemDto, RadioDto, TrboNetStreamEventDto>> entry : getStateProviders().entrySet()) {
            DeviceStateItemDto oldState = newState.get(entry.getKey());
            DeviceStateItemDto updatedState = entry.getValue().process(oldState, radio, deviceEvent.getPayload());
            if (updatedState != null) {
                newState.put(entry.getKey(), updatedState);
            } else {
                newState.remove(entry.getKey());
            }
        }

        return newState;
    }

    protected boolean isStateEvent(TrboNetStreamEventDto eventDto) {
        boolean result = false;

        if (eventDto.getDevice() != null) {
         /*
          - Connection status
          - Hardware issue
         */
            for (Map.Entry<RadioStateType, IStateProducer<DeviceStateItemDto, RadioDto, TrboNetStreamEventDto>> entry : stateProviders.entrySet()) {
                if (entry.getValue().hasStateData(eventDto)) {
                    result = true;
                }
            }
        }

        return result;
    }

    @NoArgsConstructor
    @EventPath(path = RadioUpdateUnknownEvent.PATH)
    public static class RadioUpdateUnknownEvent extends RadioUpdateEvent<TrboNetStreamEventDto> {
        public static final String PATH = "unknown";

        public RadioUpdateUnknownEvent(String radioId) {
            super(radioId);
        }

        @Override
        public String getEventPath() {
            return EventUtils.generatePath(RadioUpdateUnknownEvent.PATH, super.getEventPath());
        }
    }
}
