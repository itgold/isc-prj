package com.iscweb.service.event;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.service.eventhub.BaseEventHub;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ValidationException;

/**
 * EventHub is a central point for sending, delivering, handling asynchronous application events.
 */
@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventHub extends BaseEventHub {

    /**
     * Main application's queue qualifier.
     */
    public static final String EVENT_HUB_QUEUE_QUALIFIER = "main-queue";
    public static final String DLX_QUALIFIER = "dlx-qualifier";

    /**
     * Dead letter queue extension.
     */
    public static final String DEAD = ".dead";

    @Autowired
    private ConnectionFactory connectionFactory;

    @Value("${spring.rabbitmq.routing.exchange}")
    private String eventsExchange;

    @Value("${spring.rabbitmq.routing.queue}")
    private String eventsQueue;

    @Setter(onMethod = @__({@Autowired}))
    private RabbitTemplate rabbitTemplate;

    /**
     * Post an event to the application's queue.
     *
     * @param event to be submitted into the queue.
     * @throws ServiceException if operation failed.
     */
    public void post(IEvent<?> event) throws ServiceException {
        log.trace("Event.Exchange.Submit sending MessageId={} to Exchange={} with RoutingKey={}",
                 event.getCorrelationId(),
                 getEventsExchange(),
                 getEventsQueue());
        log.debug("POST event: {}", event.getEventPath());
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

    /**
     * Global EventHub events handler.
     *
     * @param event received event.
     */
    @RabbitListener(queues = "#{'${spring.rabbitmq.routing.queue}'}")
    public void processEventHubEvent(IEvent<?> event) throws ValidationException {
            notifySubscribers(event);
    }

    @RabbitListener(queues = "#{'${spring.rabbitmq.dead-letter.queue}'}")
    public void processFailedMessages(Message message) {
        log.error("Received failed message: {}", message.toString());
    }
}
