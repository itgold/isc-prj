package com.iscweb.integration.doors.events;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseIntegrationRawEvent;
import com.iscweb.common.events.payload.StringPayload;
import com.iscweb.common.util.EventUtils;

/**
 * Raw door device event.
 */
@EventPath(path = SaltoRawEvent.PATH)
public class SaltoRawEvent extends BaseIntegrationRawEvent<StringPayload> {

    public static final String PATH = "door.salto";

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(SaltoRawEvent.PATH, super.getEventPath());
    }
}
