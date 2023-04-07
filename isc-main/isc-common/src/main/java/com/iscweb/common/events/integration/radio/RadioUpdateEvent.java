package com.iscweb.common.events.integration.radio;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.event.radio.BaseRadioEvent;
import com.iscweb.common.model.event.radio.IRadioEventPayload;
import com.iscweb.common.util.EventUtils;
import lombok.NoArgsConstructor;

/**
 * Base application Radio device update event.
 *
 * @param <T> Event payload type.
 */
@NoArgsConstructor
@EventPath(path = RadioUpdateEvent.PATH)
public class RadioUpdateEvent<T extends IRadioEventPayload> extends BaseRadioEvent<T> {
    public static final String PATH = "update";

    public RadioUpdateEvent(String radioId) {
        setDeviceId(radioId);
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(RadioUpdateEvent.PATH, super.getEventPath());
    }
}
