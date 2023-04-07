package com.iscweb.service.event.social;

import com.fasterxml.jackson.databind.JsonNode;
import com.iscweb.common.events.social.SocialApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class YouTubeSocialEventsHandler extends BaseSocialEventsHandler {

    public List<String> extractText(JsonNode data){
        return null;
    }

    public SocialApplicationEvent.SocialApplicationEventPayload extractPayload(JsonNode data){return null;}
}
