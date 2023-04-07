package com.iscweb.common.events;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.event.IApplicationEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.util.EventUtils;

/**
 * Base class for all application Events in the system.
 *
 * @param <T> Payload type
 */
@EventPath(path = BaseApplicationEvent.PATH, root = true)
public abstract class BaseApplicationEvent<T extends ITypedPayload> extends BaseEvent<T> implements IApplicationEvent<T> {

    public static final String PATH = "event.app";

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(BaseApplicationEvent.PATH);
    }
}
