package com.iscweb.common.model.event.door;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseApplicationEvent;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.event.IDeviceEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.util.EventUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class for all Door Events in the system.
 *
 * @param <P> Payload type
 */
@Getter
@Setter
@EventPath(path = BaseDoorEvent.PATH)
public abstract class BaseDoorEvent <P extends ITypedPayload>
        extends BaseApplicationEvent<P>
        implements IDeviceEvent<P> {

    public static final String PATH = "door";

    @Getter
    @Setter
    private String deviceId;

    @Override
    public EntityType getDeviceType() {
        return EntityType.DOOR;
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(BaseDoorEvent.PATH, super.getEventPath());
    }
}
