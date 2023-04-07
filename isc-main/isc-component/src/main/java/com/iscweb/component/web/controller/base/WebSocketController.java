package com.iscweb.component.web.controller.base;

import com.iscweb.common.events.BaseApplicationAlert;
import com.iscweb.common.events.BaseApplicationEvent;
import com.iscweb.common.events.BaseServerEvent;
import com.iscweb.common.events.payload.DeviceStatePayload;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.IIncrementalUpdateEvent;
import com.iscweb.common.model.event.IIncrementalUpdatePayload;
import com.iscweb.common.model.event.IServerEvent;
import com.iscweb.common.model.event.IStateEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.IEventSubscriber;
import com.iscweb.common.util.EventUtils;
import com.iscweb.component.web.controller.IApplicationController;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Slf4j
@Controller
public class WebSocketController implements IApplicationController {

    private static final String CHANNEL_DEVICE_STATES = "/topic/deviceStates";
    private static final String CHANNEL_DEVICE_EVENTS = "/topic/deviceEvents";
    private static final String CHANNEL_SERVER_EVENTS = "/topic/serverEvents";
    private static final String CHANNEL_ALERTS = "/topic/alerts";

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SimpMessagingTemplate template;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IEventHub eventHub;

    @PostConstruct
    public void initialize() {
        // Subscribe to device estate events
        getEventHub().register(EventUtils.eventPath(BaseApplicationEvent.class, null, IStateEvent.PATH),
                (IEventSubscriber<IStateEvent<DeviceStatePayload>, DeviceStatePayload>) event -> getTemplate().convertAndSend(CHANNEL_DEVICE_STATES, event.getPayload()));

        getEventHub().register(EventUtils.eventPath(BaseApplicationEvent.class, null, IIncrementalUpdateEvent.PATH + EventUtils.PATH_WILDCARD),
                (IEventSubscriber<IIncrementalUpdateEvent<IIncrementalUpdatePayload>, IIncrementalUpdatePayload>) event -> getTemplate().convertAndSend(CHANNEL_DEVICE_EVENTS, event));

        getEventHub().register(EventUtils.generateEventPath(BaseServerEvent.class),
                (IEventSubscriber<IServerEvent<ITypedPayload>, ITypedPayload>) event -> getTemplate().convertAndSend(CHANNEL_SERVER_EVENTS, event));

        // Subscribe to alerts
        getEventHub().register(BaseApplicationAlert.class,
                (IEventSubscriber<IEvent<ITypedPayload>, ITypedPayload>) event -> getTemplate().convertAndSend(CHANNEL_ALERTS, event));
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
