package com.iscweb.common.model.event.drone;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseApplicationEvent;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.event.IDeviceEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.util.EventUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class for all Drone Events in the system.
 *
 * @param <P> Payload type
 */
@EventPath(path = BaseDroneEvent.PATH)
public abstract class BaseDroneEvent<P extends ITypedPayload>
        extends BaseApplicationEvent<P>
        implements IDeviceEvent<P> {
    public static final String PATH = "drone";

    @Getter
    @Setter
    private String deviceId;

    @Override
    public EntityType getDeviceType() {
        return EntityType.DRONE;
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(BaseDroneEvent.PATH, super.getEventPath());
    }
}
