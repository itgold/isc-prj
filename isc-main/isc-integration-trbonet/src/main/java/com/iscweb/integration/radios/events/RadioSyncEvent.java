package com.iscweb.integration.radios.events;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseExternalEntityRawEvent;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.util.EventUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Radio device sync event.
 *
 * Note: generated for each radio device while sync process
 */
@Data
@EqualsAndHashCode(callSuper = true)
@EventPath(path = RadioSyncEvent.PATH)
public class RadioSyncEvent extends BaseExternalEntityRawEvent<RadioSyncPayload> {

    public static final String PATH = "radio.sync";

    public RadioSyncEvent() {
        setEntityType(EntityType.RADIO);
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(RadioSyncEvent.PATH, super.getEventPath());
    }
}
