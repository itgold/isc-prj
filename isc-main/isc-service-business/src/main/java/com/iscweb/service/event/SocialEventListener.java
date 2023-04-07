package com.iscweb.service.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.service.event.social.TwitterSocialEventHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * SocialEventHub is a central point for sending, delivering, handling asynchronous application events.
 */
@Slf4j
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialEventListener {

    /**
     * Social application's queue qualifier.
     */
    public static final String SOCIAL_QUEUE_QUALIFIER = "social-feed-queue";
    public static final String SOCIAL_EXCHANGE_QUALIFIER = "social-feed-exchange";

    @Autowired
    private ConnectionFactory connectionFactory;

    @RabbitListener(queues = "#{'${spring.rabbitmq.social.queue}'}")
    public void processTwitterEvent(JsonNode event) throws ServiceException {
        TwitterSocialEventHandler handler = new TwitterSocialEventHandler();
        handler.handle(event);
    }
}
