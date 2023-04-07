package com.iscweb.common.model.event;

/**
 * Device event.
 *
 * @param <P> Payload type
 */
public interface IExternalEntityEvent<P extends ITypedPayload> extends IApplicationEvent<P> {
    String getExternalEntityId();
    void setExternalEntityId(String entityId);
}
