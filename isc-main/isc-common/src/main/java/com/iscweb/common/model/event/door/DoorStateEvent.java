package com.iscweb.common.model.event.door;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.payload.DeviceStatePayload;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.event.IStateEvent;
import com.iscweb.common.util.EventUtils;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Door device state change event.
 */
@NoArgsConstructor
@EventPath(path = IStateEvent.PATH)
public class DoorStateEvent
        extends BaseDoorEvent<DoorStateEvent.DoorDeviceStatePayload>
        implements IStateEvent<DoorStateEvent.DoorDeviceStatePayload> {

    public DoorStateEvent(String doorId, Set<DeviceStateItemDto> state) {
        setDeviceId(doorId);
        setPayload(new DoorDeviceStatePayload(doorId, state));
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(IStateEvent.PATH, super.getEventPath());
    }

    public static class DoorDeviceStatePayload extends DeviceStatePayload {
        public DoorDeviceStatePayload() {}
        public DoorDeviceStatePayload(String doorId, Set<DeviceStateItemDto> state) {
            super(EntityType.DOOR.name(), doorId, state);
        }
    }
}
