package com.iscweb.common.service.eventhub;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.IEventSubscriber;
import com.iscweb.common.util.EventUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@Getter
public abstract class BaseEventHub implements IEventHub {

    /**
     * A map of application subscribers.
     */
    private final Map<String, List<IEventSubscriber<?, ?>>> subscribers = Maps.newHashMap();

    /**
     * A map of RegEx patterns to match subscriber events.
     */
    private final Map<String, Pattern> patterns = Maps.newHashMap();

    /**
     * Registers all subscriber methods on {@code subscriber} to receive events.
     *
     * @param eventClazz Class of events to subscribe for.
     * @param subscriber object whose subscriber methods should be registered.
     */
    @Override
    public <T extends IEvent<?>> void register(Class<T> eventClazz, IEventSubscriber<?, ?> subscriber) {
        String eventPath = EventUtils.generateEventPath(eventClazz);
        register(eventPath, subscriber);
    }

    @Override
    public <T extends IEvent<?>> void register(String eventPath, IEventSubscriber<?, ?> subscriber) {
        List<IEventSubscriber<?, ?>> listeners = getSubscribers().get(eventPath);
        if (listeners == null) {
            listeners = Lists.newArrayList();
        }

        listeners.add(subscriber);
        getSubscribers().put(eventPath, listeners);
        getPatterns().put(eventPath, EventUtils.eventPathPattern(eventPath));
    }

    protected void notifySubscribers(IEvent<?> event) {
        String eventPath = event.getEventPath();
        log.info("!!! Observed event {}", eventPath);
        for (Map.Entry<String, List<IEventSubscriber<?, ?>>> entry : getSubscribers().entrySet()) {
            if (isMatching(eventPath, entry.getKey())) {
                log.info("   found subscriber(s) '{}' vs '{}'", eventPath, entry.getKey());
                for (IEventSubscriber<?, ?> eventSubscriber : entry.getValue()) {
                    log.info("       process subscriber {} ...", eventSubscriber.getClass().getSimpleName());
                    try {
                        @SuppressWarnings("unchecked") //cast to the specific application event type
                        IEventSubscriber<IEvent<?>, ?> subscriber =
                                (IEventSubscriber<IEvent<?>, ?>) eventSubscriber;
                        subscriber.handleApplicationEvent(event);
                    } catch (Exception e) {
                        log.error("Broken EventHub subscriber. Subscriber class: {}, subscribed event: {}",
                                eventSubscriber.getClass().getSimpleName(),  eventPath, e);
                        throw new AmqpRejectAndDontRequeueException("Broken EventHub subscriber.");
                    }
                }
            }
        }
    }

    protected boolean isMatching(String eventPath, String subscriptionPath) {
        return getPatterns()
                .get(subscriptionPath)
                .matcher(eventPath)
                .matches();
    }
}
