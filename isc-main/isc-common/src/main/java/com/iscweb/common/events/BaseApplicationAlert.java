package com.iscweb.common.events;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.util.EventUtils;

/**
 * Base class for all application Alarms in the system.
 *
 * @param <T> Payload type
 */
@EventPath(path = BaseApplicationAlert.PATH, root = true)
public abstract class BaseApplicationAlert<T extends ITypedPayload> extends BaseEvent<T> {

    public static final String PATH = "alert";

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(BaseApplicationAlert.PATH);
    }
}
