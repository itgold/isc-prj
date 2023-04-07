package com.iscweb.common.events.social;
import com.fasterxml.jackson.databind.JsonNode;
import com.iscweb.common.events.BaseApplicationEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.util.EventUtils;
import com.iscweb.common.util.JsonUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

public class SocialApplicationEvent extends BaseApplicationEvent<SocialApplicationEvent.SocialApplicationEventPayload> {
    public static final String PATH = "social";

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(SocialApplicationEvent.PATH, super.getEventPath());
    }

    @Data
    @NoArgsConstructor
    public static class SocialApplicationEventPayload implements ITypedPayload {

        private String triggeredKeyWord;
        private String triggerType;
        private Integer severity;

        private Integer authorId;
        private Integer authorSubscriberCount;

        private String authorName;
        private String authorScreenName;
        private String authorLocation;

        private String postId;
        private String postUrl;
        private Integer postViews;
        private Integer postComments;

        private ZonedDateTime eventTime;

        @Override
        public String getType() {
            return this.triggerType.toLowerCase();
        }
    }
}

