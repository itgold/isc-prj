package com.iscweb.integration.radios.events;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseExternalEntityRawEvent;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.util.EventUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * New TRBOnet radio device update event.
 *
 * Note: Generated for each radio device after receiving raw batch update from the TRBOnet system
 */
@Data
@EqualsAndHashCode(callSuper = true)
@EventPath(path = TrboNetRadioDeviceEvent.PATH)
public class TrboNetRadioDeviceEvent extends BaseExternalEntityRawEvent<TrboNetStreamEventDto> {

    public static final String PATH = "radio.trbonet";

    public TrboNetRadioDeviceEvent() {
        setEntityType(EntityType.RADIO);
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(TrboNetRadioDeviceEvent.PATH, super.getEventPath());
    }
}
