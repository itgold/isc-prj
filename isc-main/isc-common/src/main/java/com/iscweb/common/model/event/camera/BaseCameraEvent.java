package com.iscweb.common.model.event.camera;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseApplicationEvent;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.event.IDeviceEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.util.EventUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class for all Camera Events in the system.
 *
 * @param <P> Payload type
 */
@EventPath(path = BaseCameraEvent.PATH)
public abstract class BaseCameraEvent<P extends ITypedPayload> extends BaseApplicationEvent<P> implements IDeviceEvent<P> {

    public static final String PATH = "camera";

    @Getter
    @Setter
    private String deviceId;

    @Override
    public EntityType getDeviceType() {
        return EntityType.CAMERA;
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(BaseCameraEvent.PATH, super.getEventPath());
    }
}
