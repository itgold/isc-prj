package com.iscweb.common.model.event;

/**
 * Application state event.
 *
 * @param <P> Payload type
 */
public interface IStateEvent<P extends ITypedPayload> extends IDeviceEvent<P> {
    String PATH = "state";
}
