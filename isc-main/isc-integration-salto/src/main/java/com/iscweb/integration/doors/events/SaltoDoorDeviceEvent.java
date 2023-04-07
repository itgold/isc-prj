package com.iscweb.integration.doors.events;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseExternalEntityRawEvent;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.util.EventUtils;
import com.iscweb.integration.doors.models.SaltoStreamEventDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@EventPath(path = SaltoDoorDeviceEvent.PATH)
public class SaltoDoorDeviceEvent extends BaseExternalEntityRawEvent<SaltoStreamEventDto> {

    public static final String PATH = "door.salto";

    public SaltoDoorDeviceEvent() {
        setEntityType(EntityType.DOOR);
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(SaltoDoorDeviceEvent.PATH, super.getEventPath());
    }
}
