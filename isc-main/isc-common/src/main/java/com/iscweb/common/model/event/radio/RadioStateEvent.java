package com.iscweb.common.model.event.radio;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.payload.DeviceStatePayload;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.event.IStateEvent;
import com.iscweb.common.util.EventUtils;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Radio device state change event.
 */
@NoArgsConstructor
@EventPath(path = IStateEvent.PATH)
public class RadioStateEvent
        extends BaseRadioEvent<RadioStateEvent.RadioDeviceStatePayload>
        implements IStateEvent<RadioStateEvent.RadioDeviceStatePayload> {

    public RadioStateEvent(String radioId, Set<DeviceStateItemDto> state) {
        setDeviceId(radioId);
        setPayload(new RadioDeviceStatePayload(radioId, state));
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(IStateEvent.PATH, super.getEventPath());
    }

    public static class RadioDeviceStatePayload extends DeviceStatePayload implements IRadioEventPayload {
        public RadioDeviceStatePayload() {}
        public RadioDeviceStatePayload(String radioId, Set<DeviceStateItemDto> state) {
            super(EntityType.RADIO.name(), radioId, state);
        }
    }
}
