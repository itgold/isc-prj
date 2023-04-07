package com.iscweb.common.model.event.camera;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.model.event.DeviceIncrementalUpdatePayload;
import com.iscweb.common.model.event.IIncrementalUpdateEvent;
import com.iscweb.common.util.EventUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Camera devices start synchronization informative event.
 */
@NoArgsConstructor
@EventPath(path = CameraIncrementalSyncStartEvent.PATH)
public class CameraIncrementalSyncStartEvent
        extends BaseCameraEvent<CameraIncrementalSyncStartEvent.CameraDeviceUpdatePayload>
        implements IIncrementalUpdateEvent<CameraIncrementalSyncStartEvent.CameraDeviceUpdatePayload> {

    public static final String PATH = IIncrementalUpdateEvent.PATH + ".syncStart";

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(CameraIncrementalSyncStartEvent.PATH, super.getEventPath());
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class CameraDeviceUpdatePayload extends DeviceIncrementalUpdatePayload<CameraDto> {
        private boolean scheduled;
        private String user;

        public CameraDeviceUpdatePayload() {
            super(EntityType.CAMERA.name(), null, "SYNC", "Scheduled Sync", null);
        }
        public CameraDeviceUpdatePayload(boolean scheduled, String user) {
            super(EntityType.CAMERA.name(), null, "SYNC", scheduled ? "Scheduled Sync" : "Sync triggered by: " + user, null);
            this.scheduled = scheduled;
            this.user = user;
        }

        public String getDescription() {
            return scheduled ? "Scheduled Sync" : "Sync triggered by: " + user;
        }
    }
}
