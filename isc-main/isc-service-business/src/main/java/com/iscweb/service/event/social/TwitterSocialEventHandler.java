package com.iscweb.service.event.social;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.iscweb.common.events.social.SocialApplicationEvent;
import com.iscweb.common.util.JsonUtils;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TwitterSocialEventHandler extends BaseSocialEventsHandler {

    public List<String> extractText(JsonNode json) {
        ArrayList<String> result = Lists.newArrayList();
        String txt = JsonUtils.safeStringField("retweeted_status.extended_tweet.full_text", json);
        if (null != txt) {
            result.add(txt);
        }
        return result;
    }

    public SocialApplicationEvent.SocialApplicationEventPayload extractPayload(JsonNode data) {
        SocialApplicationEvent.SocialApplicationEventPayload result = new SocialApplicationEvent.SocialApplicationEventPayload();

        result.setAuthorId(JsonUtils.safeIntField("user.id", data));

        result.setAuthorName(JsonUtils.safeStringField("user.name", data));
        result.setAuthorScreenName(JsonUtils.safeStringField("user.screen_name", data));

        result.setPostId(JsonUtils.safeStringField("id_str", data));
        result.setPostUrl( JsonUtils.safeStringField("tweet_url", data));
        result.setPostViews( JsonUtils.safeIntField("favorite_count", data));
        result.setPostComments( JsonUtils.safeIntField("reply_count", data));

        String timeString = JsonUtils.safeStringField("@timestamp", data);
        result.setEventTime( null != timeString ? ZonedDateTime.parse(timeString) : null );
        return result;
    }
}
