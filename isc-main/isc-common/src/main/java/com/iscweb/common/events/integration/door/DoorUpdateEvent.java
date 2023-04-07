package com.iscweb.common.events.integration.door;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.model.event.door.BaseDoorEvent;
import com.iscweb.common.util.EventUtils;
import lombok.NoArgsConstructor;

/**
 * Base application Door device update event.
 *
 * @param <T> Event payload type.
 */
@NoArgsConstructor
@EventPath(path = DoorUpdateEvent.PATH)
public class DoorUpdateEvent<T extends ITypedPayload> extends BaseDoorEvent<T> {
    public static final String PATH = "update";

    public DoorUpdateEvent(String doorId) {
        setDeviceId(doorId);
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(DoorUpdateEvent.PATH, super.getEventPath());
    }
}
