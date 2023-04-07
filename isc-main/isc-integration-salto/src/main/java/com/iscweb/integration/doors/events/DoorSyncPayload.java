package com.iscweb.integration.doors.events;

import com.iscweb.common.events.CommonEventTypes;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.integration.doors.models.doors.OnlineDoorStatusDto;
import com.iscweb.integration.doors.models.doors.SaltoDbDoorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DoorSyncPayload implements ITypedPayload {

    private SaltoDbDoorDto data;
    private OnlineDoorStatusDto status;

    @Override
    public String getType() {
        return CommonEventTypes.DOOR_SYNC.code();
    }
}
