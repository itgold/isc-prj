package com.iscweb.common.model.event;

/**
 * Events coming from external system ISC has integration with.
 *
 * @param <P> Payload type
 */
public interface IExternalEvent<P extends ITypedPayload> extends IEvent<P> {
}
