package com.iscweb.service.event.pipeline;

import com.google.api.client.util.Lists;
import com.google.api.client.util.Maps;
import com.iscweb.common.events.BaseApplicationEvent;
import com.iscweb.common.events.integration.IExternalEntityProvider;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.alert.AlertTriggerDto;
import com.iscweb.common.model.event.DeviceIncrementalUpdatePayload;
import com.iscweb.common.model.event.IDeviceEvent;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.security.ApplicationSecurity;
import com.iscweb.common.security.ISystemUser;
import com.iscweb.common.service.IAlertTriggerProcessor;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.IEventSubscriber;
import com.iscweb.common.service.integration.IDeviceEventProcessor;
import com.iscweb.common.events.alerts.AlertMatchingContext;
import com.iscweb.service.ExternalEntityProviderFactory;
import com.iscweb.service.entity.AlertTriggerEntityService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Common alerts processing service.
 */
@Slf4j
@Service
public class AlertManagerService extends BaseEventPipelineService {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IEventHub eventHub;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private AlertTriggerEntityService alertTriggerEntityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private List<IAlertTriggerProcessor> processors;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ExternalEntityProviderFactory entityProviderFactory;

    private final Map<String, IAlertTriggerProcessor> processorsMap = Maps.newHashMap();

    /**
     * Class initialization method.
     * Registers this event service with the eventhub.
     */
    @PostConstruct
    public void initialize() {
        getEventHub().register(BaseApplicationEvent.class, (IEventSubscriber<IEvent<ITypedPayload>, ITypedPayload>) event -> {
            try (ISystemUser ignored = ApplicationSecurity.systemUserLogin(false)) {
                processEvent(null, event);
            } catch (ServiceException e) {
                log.error("Unable to process device event", e);
            }
        });

        for (IAlertTriggerProcessor processor : processors) {
            processorsMap.put(processor.getAlertType(), processor);
        }
    }


    /**
     * @see BaseEventPipelineService#processEvent(IDeviceEventProcessor, IEvent)
     */
    @Override
    protected List<IEvent<ITypedPayload>> processEvent(IDeviceEventProcessor eventProcessor, IEvent<ITypedPayload> inputEvent) throws ServiceException {
        List<AlertTriggerDto> triggers = getAlertTriggerEntityService().activeConfigs();
        AlertMatchingContext context = createContext(inputEvent);
        for (AlertTriggerDto trigger : triggers) {
            IAlertTriggerProcessor processor = processorsMap.get(trigger.getProcessorType());
            if (processor != null) {
                if (trigger.isMatching(context)) {
                    List<IEvent<? extends ITypedPayload>> events = processor.process(trigger, context);
                    if (events != null) {
                        for (IEvent<? extends ITypedPayload> event : events) {
                            getEventHub().post(event);
                        }
                    }
                }
            }
        }

        return Collections.emptyList();
    }

    protected AlertMatchingContext createContext(IEvent<ITypedPayload> inputEvent) {
        AlertMatchingContext context = new AlertMatchingContext();
        context.setEvent(inputEvent);
        if (inputEvent instanceof IDeviceEvent) {
            context.setDeviceId(((IDeviceEvent<?>) inputEvent).getDeviceId());
        }

        if (inputEvent.getPayload() != null && inputEvent.getPayload() instanceof DeviceIncrementalUpdatePayload) {
            DeviceIncrementalUpdatePayload payload = (DeviceIncrementalUpdatePayload) inputEvent.getPayload();
            EntityType entityType = !Strings.isBlank(payload.getType()) ? EntityType.valueOf(payload.getType()) : null;
            IExternalEntityProvider provider = entityType != null ? getEntityProviderFactory().resolveProvider(entityType) : null;
            if (provider != null && !Strings.isEmpty(payload.getDeviceId())) {
                context.setDevice(provider.resolveEntityById(payload.getDeviceId()));
                context.setDeviceState(provider.resolveDeviceState(context.getDevice()));
            }
        }

        return context;
    }

    public List<IAlertTriggerProcessor> listAlertTriggerProcessors() {
        return Lists.newArrayList(processorsMap.values());
    }
}
