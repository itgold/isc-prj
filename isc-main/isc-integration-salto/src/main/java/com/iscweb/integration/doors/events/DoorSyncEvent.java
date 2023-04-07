package com.iscweb.integration.doors.events;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseExternalEntityRawEvent;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.util.EventUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@EventPath(path = DoorSyncEvent.PATH)
public class DoorSyncEvent extends BaseExternalEntityRawEvent<DoorSyncPayload> {

    public static final String PATH = "door.sync";

    public DoorSyncEvent() {
        setEntityType(EntityType.DOOR);
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(DoorSyncEvent.PATH, super.getEventPath());
    }
}
