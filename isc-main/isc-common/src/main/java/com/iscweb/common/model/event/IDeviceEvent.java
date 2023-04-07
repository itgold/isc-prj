package com.iscweb.common.model.event;

import com.iscweb.common.model.EntityType;

/**
 * Device event.
 *
 * @param <P> Payload type
 */
public interface IDeviceEvent<P extends ITypedPayload> extends IApplicationEvent<P> {

    String getDeviceId();

    void setDeviceId(String deviceId);

    EntityType getDeviceType();
}
