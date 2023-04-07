package com.iscweb.service.listener;

import com.iscweb.common.events.BaseApplicationAlert;
import com.iscweb.common.events.BaseApplicationEvent;
import com.iscweb.common.events.BaseExternalEntityRawEvent;
import com.iscweb.common.events.BaseIntegrationRawEvent;
import com.iscweb.common.events.payload.DeviceStatePayload;
import com.iscweb.common.model.event.IApplicationEvent;
import com.iscweb.common.model.event.IExternalEntityEvent;
import com.iscweb.common.model.event.IExternalEvent;
import com.iscweb.common.model.event.IStateEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.security.ApplicationSecurity;
import com.iscweb.common.security.ISystemUser;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.IEventSubscriber;
import com.iscweb.common.util.EventUtils;
import com.iscweb.service.search.EventsHistorySearchService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class EventLoggerListener {
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IEventHub eventHub;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private EventsHistorySearchService eventsHistorySearchService;

    @PostConstruct
    public void initialize() {
        getEventHub().register(BaseApplicationEvent.class, (IEventSubscriber<IApplicationEvent<ITypedPayload>, ITypedPayload>) event -> {
            try (ISystemUser ignored = ApplicationSecurity.systemUserLogin(false)) {
                getEventsHistorySearchService().recordApplicationEvent(event);
            }
        });

        getEventHub().register(EventUtils.eventPath(BaseApplicationEvent.class, null, IStateEvent.PATH),
                (IEventSubscriber<IStateEvent<DeviceStatePayload>, DeviceStatePayload>) event -> {
            try (ISystemUser ignored = ApplicationSecurity.systemUserLogin(false)) {
                getEventsHistorySearchService().recordDeviceState(event);
            } catch (Exception e) {
                log.error("Unable to log event", e);
            }
        });

        getEventHub().register(BaseApplicationAlert.class, (IEventSubscriber<IApplicationEvent<ITypedPayload>, ITypedPayload>) event -> {
            try (ISystemUser ignored = ApplicationSecurity.systemUserLogin(false)) {
                getEventsHistorySearchService().recordApplicationEvent(event);
            }
        });

        getEventHub().register(BaseIntegrationRawEvent.class, (IEventSubscriber<IExternalEvent<ITypedPayload>, ITypedPayload>) event -> {
            try (ISystemUser ignored = ApplicationSecurity.systemUserLogin(false)) {
                getEventsHistorySearchService().recordRawEvent(event);
            } catch (Exception e) {
                log.error("Unable to log event", e);
            }
        });
        getEventHub().register(BaseExternalEntityRawEvent.class, (IEventSubscriber<IExternalEntityEvent<ITypedPayload>, ITypedPayload>) event -> {
            try (ISystemUser ignored = ApplicationSecurity.systemUserLogin(false)) {
                getEventsHistorySearchService().recordRawEvent(event);
            }
        });
    }
}
