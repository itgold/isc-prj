package com.iscweb.integration.radios.events;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseIntegrationRawEvent;
import com.iscweb.common.events.payload.StringPayload;
import com.iscweb.common.util.EventUtils;

/**
 * Raw radio device event.
 */
@EventPath(path = TrboNetRawEvent.PATH)
public class TrboNetRawEvent extends BaseIntegrationRawEvent<StringPayload> {

    public static final String PATH = "radio.trbonet";

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(TrboNetRawEvent.PATH, super.getEventPath());
    }
}
