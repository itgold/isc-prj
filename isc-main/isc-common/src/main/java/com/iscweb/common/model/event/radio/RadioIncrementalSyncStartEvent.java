package com.iscweb.common.model.event.radio;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.entity.core.RadioDto;
import com.iscweb.common.model.event.DeviceIncrementalUpdatePayload;
import com.iscweb.common.model.event.IIncrementalUpdateEvent;
import com.iscweb.common.util.EventUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Radio devices start synchronization informative event.
 */
@NoArgsConstructor
@EventPath(path = RadioIncrementalSyncStartEvent.PATH)
public class RadioIncrementalSyncStartEvent
        extends BaseRadioEvent<RadioIncrementalSyncStartEvent.RadioDeviceUpdatePayload>
        implements IIncrementalUpdateEvent<RadioIncrementalSyncStartEvent.RadioDeviceUpdatePayload> {

    public static final String PATH = IIncrementalUpdateEvent.PATH + ".syncStart";

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(RadioIncrementalSyncStartEvent.PATH, super.getEventPath());
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class RadioDeviceUpdatePayload extends DeviceIncrementalUpdatePayload<RadioDto> implements IRadioEventPayload {
        private boolean scheduled;
        private String user;

        public RadioDeviceUpdatePayload() {
            super(EntityType.RADIO.name(), null, "SYNC", "Scheduled Sync", null);
        }
        public RadioDeviceUpdatePayload(boolean scheduled, String user) {
            super(EntityType.RADIO.name(), null, "SYNC", scheduled ? "Scheduled Sync" : "Sync triggered by: " + user, null);
            this.scheduled = scheduled;
            this.user = user;
        }

        public String getDescription() {
            return scheduled ? "Scheduled Sync" : "Sync triggered by: " + user;
        }
    }
}
