package com.iscweb.integration.cameras.mock.events;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseExternalEntityRawEvent;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.util.EventUtils;
import com.iscweb.integration.cameras.mock.services.streaming.events.MipCameraStatusEventDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@EventPath(path = MipCameraDeviceEvent.PATH)
public class MipCameraDeviceEvent extends BaseExternalEntityRawEvent<MipCameraStatusEventDto> {

    public static final String PATH = "camera.mip";

    public MipCameraDeviceEvent() {
        setEntityType(EntityType.CAMERA);
    }

    public MipCameraDeviceEvent(String cameraId) {
        this();

        this.setExternalEntityId(cameraId);
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(MipCameraDeviceEvent.PATH, super.getEventPath());
    }
}
