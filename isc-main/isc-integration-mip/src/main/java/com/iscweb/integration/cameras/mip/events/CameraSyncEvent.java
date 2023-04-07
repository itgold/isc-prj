package com.iscweb.integration.cameras.mip.events;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseExternalEntityRawEvent;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.util.EventUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@EventPath(path = CameraSyncEvent.PATH)
public class CameraSyncEvent extends BaseExternalEntityRawEvent<CameraSyncPayload> {

    public static final String PATH = "camera.sync";

    public CameraSyncEvent() {
        setEntityType(EntityType.CAMERA);
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(CameraSyncEvent.PATH, super.getEventPath());
    }
}
