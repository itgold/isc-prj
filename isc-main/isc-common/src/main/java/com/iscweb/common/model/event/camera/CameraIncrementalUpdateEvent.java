package com.iscweb.common.model.event.camera;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.model.event.DeviceIncrementalUpdatePayload;
import com.iscweb.common.model.event.IIncrementalUpdateEvent;
import com.iscweb.common.util.EventUtils;
import lombok.NoArgsConstructor;

/**
 * Door device state change event.
 */
@NoArgsConstructor
@EventPath(path = IIncrementalUpdateEvent.PATH)
public class CameraIncrementalUpdateEvent
        extends BaseCameraEvent<CameraIncrementalUpdateEvent.CameraDeviceUpdatePayload>
        implements IIncrementalUpdateEvent<CameraIncrementalUpdateEvent.CameraDeviceUpdatePayload> {

    public CameraIncrementalUpdateEvent(String cameraId) {
        setDeviceId(cameraId);
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(IIncrementalUpdateEvent.PATH, super.getEventPath());
    }

    public static class CameraDeviceUpdatePayload extends DeviceIncrementalUpdatePayload<CameraDto> {
        public CameraDeviceUpdatePayload() {}
        public CameraDeviceUpdatePayload(String cameraId, CameraDto cameraDto, String code, String description) {
            super(EntityType.CAMERA.name(), cameraId, code, description, cameraDto);
        }
    }
}
