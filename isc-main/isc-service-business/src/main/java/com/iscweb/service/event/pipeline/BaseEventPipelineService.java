package com.iscweb.service.event.pipeline;

import com.iscweb.common.events.integration.IExternalEntityProvider;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.service.IApplicationSecuredService;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.integration.IDeviceEventProcessor;
import com.iscweb.common.util.EventUtils;
import com.iscweb.service.ExternalEntityProviderFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Base class for the event pipeline services.
 * Event pipeline services are system services that are used to handle system events received from the EventHub.
 */
@Slf4j
public abstract class BaseEventPipelineService implements IApplicationSecuredService {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IEventHub eventHub;

    @Getter
    @Setter(onMethod = @__({@Autowired(required = false)}))
    private List<IDeviceEventProcessor> eventProcessors;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ExternalEntityProviderFactory entityProviderFactory;

    /**
     * Primary method that is used to process a specific input event by a specific event processor.
     * @param inputEvent input application level event.
     * @param <I> type of the input application event.
     */
    protected <I extends IEvent<ITypedPayload>> void process(I inputEvent) {
        boolean processed = false;

        if (!CollectionUtils.isEmpty(getEventProcessors())) {
            for (IDeviceEventProcessor processor : getEventProcessors()) {
                if (processor.isSupported(inputEvent)) {
                    try {
                        List<IEvent<ITypedPayload>> events = processEvent(processor, inputEvent);
                        if (!CollectionUtils.isEmpty(events)) {
                            for (IEvent<ITypedPayload> e : events) {
                                getEventHub().post(e);
                            }
                        }
                    } catch (Exception e) {
                        log.error("Unable to process device event", e);
                    }

                    processed = true;
                }
            }
        }

        if (!processed) {
            log.warn("No converter found for a device event {}", EventUtils.eventPath(inputEvent));
        }
    }

    /**
     * Template method for processing given event.
     * @param processor specific device event processor for a given event.
     * @param inputEvent input application event.
     * @return a list of application level events.
     *
     * @throws ServiceException if operation failed.
     */
    protected abstract List<IEvent<ITypedPayload>> processEvent(IDeviceEventProcessor processor, IEvent<ITypedPayload> inputEvent) throws ServiceException;

    /**
     * Resolves a suitable device provider by a given type of the device.
     * @see IExternalEntityProvider
     *
     * @param entityType device type to resolve device provider for.
     * @return a suitable implementation of the device provider.
     */
    protected IExternalEntityProvider resolveProvider(EntityType entityType) {
        return getEntityProviderFactory().resolveProvider(entityType);
    }
}
