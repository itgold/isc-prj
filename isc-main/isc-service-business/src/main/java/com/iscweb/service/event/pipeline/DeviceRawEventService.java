package com.iscweb.service.event.pipeline;

import com.google.api.client.util.Lists;
import com.iscweb.common.events.BaseIntegrationRawEvent;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.security.ApplicationSecurity;
import com.iscweb.common.security.ISystemUser;
import com.iscweb.common.service.IEventSubscriber;
import com.iscweb.common.service.integration.IDeviceEventProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
public class DeviceRawEventService extends BaseEventPipelineService {

    /**
     * Class initialization method.
     * Registers this event service with the eventhub.
     */
    @PostConstruct
    public void initialize() {
        getEventHub().register(BaseIntegrationRawEvent.class, (IEventSubscriber<IEvent<ITypedPayload>, ITypedPayload>) event -> {
            try (ISystemUser ignored = ApplicationSecurity.systemUserLogin(false)) {
                process(event);
            } catch (Exception e) {
                log.error("Unable to process device event", e);
            }
        });
    }

    /**
     * @see BaseEventPipelineService#processEvent(IDeviceEventProcessor, IEvent)
     */
    @Override
    protected List<IEvent<ITypedPayload>> processEvent(IDeviceEventProcessor processor, IEvent<ITypedPayload> inputEvent) throws ServiceException {
        return Lists.newArrayList(processor.parseRawEvents((BaseIntegrationRawEvent<ITypedPayload>) inputEvent));
    }

}
