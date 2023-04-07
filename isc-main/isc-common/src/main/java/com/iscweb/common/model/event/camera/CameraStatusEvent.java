package com.iscweb.common.model.event.camera;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.util.EventUtils;

/**
 * Camera status update event.
 */
@EventPath(path = CameraStatusEvent.PATH)
public class CameraStatusEvent extends BaseCameraEvent<CameraEventPayload> {

    public static final String PATH = "cameraStatus";

    public CameraStatusEvent() {
    }

    public CameraStatusEvent(String cameraId) {
        setDeviceId(cameraId);
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(CameraStatusEvent.PATH, super.getEventPath());
    }
}
