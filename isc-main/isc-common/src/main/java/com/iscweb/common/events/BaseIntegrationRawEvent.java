package com.iscweb.common.events;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.event.IExternalEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.util.EventUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Base class for all integration raw events.
 *
 * It is not guaranteed that the third party system will provide single event per device. Most of the time for sake of
 * performance the third party systems we are listening events from are batching device events in one call. So, this base
 * class is to represent such raw events.
 *
 * @param <P> The event payload type. See <code>com.iscweb.common.events.payload.StringPayload</code> for example.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@EventPath(path = BaseIntegrationRawEvent.PATH, root = true)
public abstract class BaseIntegrationRawEvent<P extends ITypedPayload> extends BaseEvent<P> implements IExternalEvent<P> {

    public static final String PATH = "event.raw.native";

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(BaseIntegrationRawEvent.PATH);
    }
}
