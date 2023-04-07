package com.iscweb.common.model.event.drone;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.payload.DeviceStatePayload;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.event.IStateEvent;
import com.iscweb.common.util.EventUtils;

import java.util.Set;

/**
 * Drone device state change event.
 */
@EventPath(path = IStateEvent.PATH)
public class DroneStateEvent
        extends BaseDroneEvent<DroneStateEvent.DroneDeviceStatePayload>
        implements IStateEvent<DroneStateEvent.DroneDeviceStatePayload> {

    public DroneStateEvent() {
    }

    public DroneStateEvent(String droneId, Set<DeviceStateItemDto> state) {
        setDeviceId(droneId);
        setPayload(new DroneDeviceStatePayload(droneId, state));
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(IStateEvent.PATH, super.getEventPath());
    }

    public static class DroneDeviceStatePayload extends DeviceStatePayload {
        public DroneDeviceStatePayload() {}
        public DroneDeviceStatePayload(String droneId, Set<DeviceStateItemDto> state) {
            super(EntityType.DRONE.name(), droneId, state);
        }
    }
}
