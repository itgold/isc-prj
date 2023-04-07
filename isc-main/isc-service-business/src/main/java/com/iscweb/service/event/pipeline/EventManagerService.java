package com.iscweb.service.event.pipeline;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.iscweb.common.events.BaseExternalEntityRawEvent;
import com.iscweb.common.events.integration.DeviceEventConverterResult;
import com.iscweb.common.events.integration.IExternalEntityProvider;
import com.iscweb.common.events.integration.OnStaleEvent;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.IDeviceStateDto;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.event.IDeviceEvent;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.security.ApplicationSecurity;
import com.iscweb.common.security.ISystemUser;
import com.iscweb.common.service.IEventSubscriber;
import com.iscweb.common.service.integration.IDeviceEventProcessor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Common raw device events processing service.
 */
@Slf4j
@Service
public class EventManagerService extends BaseEventPipelineService {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ObjectMapper objectMapper;

    /**
     * Class initialization method.
     * Registers this event service with the eventhub.
     */
    @PostConstruct
    public void initialize() {
        getEventHub().register(BaseExternalEntityRawEvent.class, (IEventSubscriber<IEvent<ITypedPayload>, ITypedPayload>) event -> {
            try (ISystemUser ignored = ApplicationSecurity.systemUserLogin(false)) {
                process(event);
            }
        });
    }

    /**
     * @see BaseEventPipelineService#processEvent(IDeviceEventProcessor, IEvent)
     */
    @Override
    @SuppressWarnings("unchecked")
    protected List<IEvent<ITypedPayload>> processEvent(IDeviceEventProcessor processor, IEvent<ITypedPayload> inputEvent) throws ServiceException {
        List<IEvent<ITypedPayload>> result = Lists.newArrayList();

        BaseExternalEntityRawEvent<ITypedPayload> deviceEvent = (BaseExternalEntityRawEvent<ITypedPayload>) inputEvent;
        IExternalEntityProvider provider = deviceEvent.getEntityType() != null ? resolveProvider(deviceEvent.getEntityType()) : null;
        if (provider != null) {
            IExternalEntityDto device = provider.resolveEntityByExternalId(deviceEvent.getExternalEntityId());
            IDeviceStateDto deviceState = provider.resolveDeviceState(device);
            ZonedDateTime updateTime = device != null ? device.getLastSyncTime() : null;
            ZonedDateTime eventTime = deviceEvent.getEventTime() != null ? deviceEvent.getEventTime() : ZonedDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss Z");
            log.warn("Process event {}. Device prev time: {}, event time: {}",
                     deviceEvent.getEventPath(),
                     updateTime != null ? updateTime.withZoneSameInstant(ZoneId.systemDefault()).format(formatter) : "null",
                     deviceEvent.getEventTime() != null ? deviceEvent.getEventTime().withZoneSameInstant(ZoneId.systemDefault()).format(formatter) : "null");

            if (updateTime == null || updateTime.isBefore(eventTime)) {
                DeviceEventConverterResult converterResult = processor.process(deviceEvent, device, deviceState);
                if (converterResult != null) {
                    if (converterResult.getUpdatedDevice() != null || converterResult.getUpdatedDeviceState() != null) {
                        device = provider.update(device,
                                converterResult.getUpdatedDevice(),
                                converterResult.getUpdatedDeviceState());
                    }
                    if (!CollectionUtils.isEmpty(converterResult.getEvents())) {
                        final String deviceId = device != null ? device.getEntityId() : null;
                        converterResult.getEvents()
                                .forEach(outEvent -> {
                                    if (deviceId != null && outEvent instanceof IDeviceEvent) {
                                        ((IDeviceEvent<ITypedPayload>) outEvent).setDeviceId(deviceId);
                                    }
                                    result.add((IEvent<ITypedPayload>) outEvent);
                                });
                    }
                } else {
                    log.error("!!! IGNORE EVENT '{}' !!!", inputEvent.getEventPath());
                }
            } else {
                OnStaleEvent event = new OnStaleEvent();
                event.setCorrelationId(inputEvent.getCorrelationId());
                event.setReferenceId(inputEvent.getEventId());
                event.setPayload(new OnStaleEvent.OnStaleEventPayload());
                event.getPayload().setLastSyncTime(updateTime);
                event.getPayload().setEventType(inputEvent.getEventPath());
                event.getPayload().setEventTime(inputEvent.getEventTime());
                result.add((IEvent<ITypedPayload>) (IEvent<? extends ITypedPayload>) event);
                log.error("!!! IGNORE EVENT '{}' !!!", inputEvent.getEventPath());
            }
        } else {
            try {
                log.error("Unable to process event {}", getObjectMapper().writeValueAsString(deviceEvent));
            } catch (Throwable e) {
                log.error("Unable to process event", e);
            }
        }

        return result;
    }

}
