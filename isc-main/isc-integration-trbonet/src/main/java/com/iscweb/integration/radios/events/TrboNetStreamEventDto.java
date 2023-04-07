package com.iscweb.integration.radios.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.common.model.event.radio.IRadioEventPayload;
import com.iscweb.integration.radios.models.RadioDeviceDto;
import com.iscweb.integration.radios.models.TrboNetDeviceUpdateType;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * Parsed event Dto received from TRBOnet system.
 */
@Data
public class TrboNetStreamEventDto implements IRadioEventPayload {

    @JsonProperty("Device")
    private RadioDeviceDto device;

    @JsonProperty("UpdateType")
    private TrboNetDeviceUpdateType updateType;

    @JsonProperty("EventTime")
    private ZonedDateTime eventTime;

    @Override
    public String getType() {
        return TrboNetEventTypes.EVENT_STREAM.code();
    }
}
