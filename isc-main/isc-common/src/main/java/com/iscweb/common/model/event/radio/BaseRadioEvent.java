package com.iscweb.common.model.event.radio;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseApplicationEvent;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.event.IDeviceEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.util.EventUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class for all Radio Events in the system.
 *
 * @param <P> Payload type
 */
@Getter
@Setter
@EventPath(path = BaseRadioEvent.PATH)
public abstract class BaseRadioEvent<P extends IRadioEventPayload>
        extends BaseApplicationEvent<P>
        implements IDeviceEvent<P> {

    public static final String PATH = "radio";

    @Getter
    @Setter
    private String deviceId;

    @Override
    public EntityType getDeviceType() {
        return EntityType.RADIO;
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(BaseRadioEvent.PATH, super.getEventPath());
    }
}
