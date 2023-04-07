package com.iscweb.common.model.event;

/**
 * Application event.
 *
 * @param <P> Payload type
 */
public interface IApplicationEvent<P extends ITypedPayload> extends IEvent<P> {
}
