package com.iscweb.service.event.social;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.iscweb.common.events.social.SocialApplicationEvent;
import com.iscweb.common.events.social.SocialFilterType;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.service.IEventHub;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Common Social Application Event processing service
 */
abstract public class BaseSocialEventsHandler implements ISocialEventsHandler {

    private static final String PATTERN_PREFIX = ".*";

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IEventHub eventHub;

    protected List<String> badWords = Arrays.asList("kill", "die", "trump", "red");

    protected abstract List<String> extractText(JsonNode data);

    protected abstract SocialApplicationEvent.SocialApplicationEventPayload extractPayload(JsonNode data);

    /**
     * Common method that is invoked from the child class
     */
    public void handle(JsonNode data) throws ServiceException {
        List<IEvent<? extends ITypedPayload>> events = processEvent(data);
        for (IEvent event : events) {
            getEventHub().post(event);
        }
    }

    /**
     * Class method responsible for SocialApplicationEvent generation
     */
    protected List<IEvent<? extends ITypedPayload>> processEvent(JsonNode data) {
        List<IEvent<? extends ITypedPayload>> events = Lists.newArrayList();
        List<String> text = extractText(data);
        if (null != text) {
            text.forEach(textStr -> {
                for (String patterString : badWords) {
                    Pattern pattern = Pattern.compile(PATTERN_PREFIX + patterString + PATTERN_PREFIX, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(textStr);
                    if (matcher.matches()) {
                        SocialApplicationEvent event = new SocialApplicationEvent();
                        event.setOrigin(this.getClass().getSimpleName());

                        SocialApplicationEvent.SocialApplicationEventPayload payload = extractPayload(data);
                        event.setPayload(payload);

                        event.getPayload().setTriggerType(SocialFilterType.BAD_WORDS.toString());
                        event.getPayload().setTriggeredKeyWord(patterString);
                        event.setEventTime(event.getPayload().getEventTime());
                        event.setReceivedTime(ZonedDateTime.now());

                        event.setCorrelationId(event.getPayload().getPostId());
                        event.getPayload().setSeverity(1);
                        events.add(event);
                    }
                }
            });
        }

        return events;
    }

}
