package com.iscweb.common.model.event.door;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.event.DeviceIncrementalUpdatePayload;
import com.iscweb.common.model.event.IIncrementalUpdateEvent;
import com.iscweb.common.util.EventUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Door devices start synchronization informative event.
 */
@NoArgsConstructor
@EventPath(path = DoorIncrementalSyncStartEvent.PATH)
public class DoorIncrementalSyncStartEvent
        extends BaseDoorEvent<DoorIncrementalSyncStartEvent.DoorDeviceUpdatePayload>
        implements IIncrementalUpdateEvent<DoorIncrementalSyncStartEvent.DoorDeviceUpdatePayload> {

    public static final String PATH = IIncrementalUpdateEvent.PATH + ".syncStart";

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(DoorIncrementalSyncStartEvent.PATH, super.getEventPath());
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class DoorDeviceUpdatePayload extends DeviceIncrementalUpdatePayload<DoorDto> {
        private boolean scheduled;
        private String user;

        public DoorDeviceUpdatePayload() {
            super(EntityType.DOOR.name(), null, "SYNC", "Scheduled Sync", null);
        }
        public DoorDeviceUpdatePayload(boolean scheduled, String user) {
            super(EntityType.DOOR.name(), null, "SYNC", scheduled ? "Scheduled Sync" : "Sync triggered by: " + user, null);
            this.scheduled = scheduled;
            this.user = user;
        }

        public String getDescription() {
            return scheduled ? "Scheduled Sync" : "Sync triggered by: " + user;
        }
    }
}
