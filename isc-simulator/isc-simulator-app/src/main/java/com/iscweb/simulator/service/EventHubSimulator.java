package com.iscweb.simulator.service;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.service.IEventSubscriber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * EventHubSimulator
 *
 * This class is responsible for publishing simulated Salto Events
 * to the eventHub for further processing by the system.
 *
 * This class does not subscribe, just publish.
 *
 * @author Denis Morozov, Aleksander Rezen
 * Date: 3/15/21
 */
@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventHubSimulator implements IEventHub {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Value("${spring.rabbitmq.routing.exchange}")
    private String eventsExchange;

    @Value("${spring.rabbitmq.routing.queue}")
    private String eventsQueue;

    @Setter(onMethod = @__({@Autowired}))
    private RabbitTemplate rabbitTemplate;

    @Override
    public void post(IEvent<?> event) throws ServiceException {
        log.trace("Event.Exchange.Submit sending MessageId={} to Exchange={} with RoutingKey={}",
                event.getCorrelationId(),
                getEventsExchange(),
                getEventsQueue());
        try {
            getRabbitTemplate().convertAndSend(getEventsExchange(), getEventsQueue(), event);
        } catch (AmqpException ex) {
            log.error("Event.Exchange.Submit.Failed: EventId={} ExchangeName={} RoutingKey={}",
                    event.getCorrelationId(),
                    getEventsExchange(),
                    getEventsQueue());
            throw new ServiceException("Unable to send message to rabbitMQ", ex);
        }
        log.trace("Event.Exchange.Submit.Success: EventId={} ExchangeName={} RoutingKey={}",
                event.getCorrelationId(),
                getEventsExchange(),
                getEventsQueue());
    }

    @Override
    public <T extends IEvent<?>> void register(Class<T> eventClazz, IEventSubscriber<?, ?> subscriber) {

    }

    @Override
    public <T extends IEvent<?>> void register(String eventPath, IEventSubscriber<?, ?> subscriber) {

    }
}
