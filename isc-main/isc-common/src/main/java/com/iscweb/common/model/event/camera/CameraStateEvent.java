package com.iscweb.common.model.event.camera;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.payload.DeviceStatePayload;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.event.IStateEvent;
import com.iscweb.common.util.EventUtils;

import java.util.Set;

/**
 * Camera device state change event.
 */
@EventPath(path = IStateEvent.PATH)
public class CameraStateEvent
        extends BaseCameraEvent<CameraStateEvent.CameraDeviceStatePayload>
        implements IStateEvent<CameraStateEvent.CameraDeviceStatePayload> {

    public CameraStateEvent() {
    }

    public CameraStateEvent(String cameraId, Set<DeviceStateItemDto> state) {
        setDeviceId(cameraId);
        setPayload(new CameraDeviceStatePayload(cameraId, state));
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(IStateEvent.PATH, super.getEventPath());
    }

    public static class CameraDeviceStatePayload extends DeviceStatePayload {
        public CameraDeviceStatePayload() {}

        public CameraDeviceStatePayload(String cameraId, Set<DeviceStateItemDto> state) {
            super(EntityType.CAMERA.name(), cameraId, state);
        }
    }
}
