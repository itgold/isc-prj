package com.iscweb.common.model.event;

/**
 * Application incremental update event.
 *
 * @param <P> Payload type
 */
public interface IIncrementalUpdateEvent<P extends IIncrementalUpdatePayload> extends IDeviceEvent<P> {
    String PATH = "incrementalUpdate";
}
