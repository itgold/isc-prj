package com.iscweb.common.events.integration.camera;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.event.camera.BaseCameraEvent;
import com.iscweb.common.util.EventUtils;

/**
 * Base application Camera device update event.
 */
@EventPath(path = CameraUpdateEvent.PATH)
public class CameraUpdateEvent extends BaseCameraEvent<CameraUpdatePayload> {
    public static final String PATH = "update";

    public CameraUpdateEvent() {
    }

    public CameraUpdateEvent(String cameraId) {
        setDeviceId(cameraId);
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(CameraUpdateEvent.PATH, super.getEventPath());
    }
}
