package com.iscweb.common.model.event.door;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.event.DeviceIncrementalUpdatePayload;
import com.iscweb.common.model.event.IIncrementalUpdateEvent;
import com.iscweb.common.util.EventUtils;
import lombok.NoArgsConstructor;

/**
 * Door device state change event.
 */
@NoArgsConstructor
@EventPath(path = IIncrementalUpdateEvent.PATH)
public class DoorIncrementalUpdateEvent
        extends BaseDoorEvent<DoorIncrementalUpdateEvent.DoorDeviceUpdatePayload>
        implements IIncrementalUpdateEvent<DoorIncrementalUpdateEvent.DoorDeviceUpdatePayload> {

    public DoorIncrementalUpdateEvent(String doorId) {
        setDeviceId(doorId);
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(IIncrementalUpdateEvent.PATH, super.getEventPath());
    }

    public static class DoorDeviceUpdatePayload extends DeviceIncrementalUpdatePayload<DoorDto> {
        public DoorDeviceUpdatePayload() {}
        public DoorDeviceUpdatePayload(String doorId, DoorDto doorDto, String code, String description) {
            super(EntityType.DOOR.name(), doorId, code, description, doorDto);
        }
    }
}
