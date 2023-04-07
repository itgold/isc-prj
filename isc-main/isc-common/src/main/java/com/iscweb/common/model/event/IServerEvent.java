package com.iscweb.common.model.event;

/**
 * Application incremental update event.
 *
 * @param <P> Payload type
 */
public interface IServerEvent<P extends ITypedPayload> extends IApplicationEvent<P> {
    String PATH = "serverEvent";
}
