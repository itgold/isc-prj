package com.iscweb.common.events;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.util.EventUtils;

/**
 * Base class for all application social events in the system.
 *
 * @param <T> Payload type
 */
@EventPath(path = BaseSocialEvent.PATH, root = true)
public abstract class BaseSocialEvent<T extends ITypedPayload> extends BaseEvent<T> {

    public static final String PATH = "social";

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(BaseSocialEvent.PATH);
    }
}
