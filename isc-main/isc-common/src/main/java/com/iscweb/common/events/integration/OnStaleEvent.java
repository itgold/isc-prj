package com.iscweb.common.events.integration;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseApplicationEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.util.EventUtils;
import lombok.Data;

import java.time.ZonedDateTime;

@EventPath(path = OnStaleEvent.PATH)
public class OnStaleEvent extends BaseApplicationEvent<OnStaleEvent.OnStaleEventPayload>  {

    public static final String PATH = "onStaleEvent";

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(OnStaleEvent.PATH, super.getEventPath());
    }

    @Data
    public static class OnStaleEventPayload implements ITypedPayload {

        private ZonedDateTime lastSyncTime;

        private ZonedDateTime eventTime;

        private String eventType;

        @Override
        public String getType() {
            return OnStaleEvent.PATH;
        }
    }
}
