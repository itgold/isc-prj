package com.iscweb.common.model.event.radio;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.entity.core.RadioDto;
import com.iscweb.common.model.event.DeviceIncrementalUpdatePayload;
import com.iscweb.common.model.event.IIncrementalUpdateEvent;
import com.iscweb.common.util.EventUtils;
import lombok.NoArgsConstructor;

/**
 * Radio device state change event.
 */
@NoArgsConstructor
@EventPath(path = IIncrementalUpdateEvent.PATH)
public class RadioIncrementalUpdateEvent
        extends BaseRadioEvent<RadioIncrementalUpdateEvent.RadioDeviceUpdatePayload>
        implements IIncrementalUpdateEvent<RadioIncrementalUpdateEvent.RadioDeviceUpdatePayload> {

    public RadioIncrementalUpdateEvent(String radioId) {
        setDeviceId(radioId);
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(IIncrementalUpdateEvent.PATH, super.getEventPath());
    }

    public static class RadioDeviceUpdatePayload extends DeviceIncrementalUpdatePayload<RadioDto> implements IRadioEventPayload {
        public RadioDeviceUpdatePayload() {}
        public RadioDeviceUpdatePayload(String radioId, RadioDto radioDto, String code, String description) {
            super(EntityType.RADIO.name(), radioId, code, description, radioDto);
        }
    }
}
