package com.iscweb.common.model.event;

import com.iscweb.common.util.StringUtils;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Base contract for all events in the ISC system.
 *
 * @param <P> Payload type
 */
public interface IEvent<P extends ITypedPayload> extends Serializable {

    String getEventId();
    String getCorrelationId();
    String getReferenceId();
    String getOrigin();

    P getPayload();

    ZonedDateTime getEventTime();

    ZonedDateTime getReceivedTime();

    default String getEventPath() {
        return StringUtils.EMPTY;
    }

}
