package com.iscweb.integration.radios.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iscweb.common.events.payload.StringPayload;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.sis.impl.BaseSisEventListeningService;
import com.iscweb.integration.radios.events.TrboNetRawEvent;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

/**
 * TRBOnet implementation for event processing.
 *
 * Note: TRBOnet raw events are simple JSON payloads.
 * We are not parsing TRBOnet events at this level. Just wrap them up as device events with raw payload
 * and pass for further processing by the system
 */
public class TrboNetEventListeningService extends BaseSisEventListeningService<TrboNetRawEvent, StringPayload, String> {

    public TrboNetEventListeningService(IEventHub eventHub, ObjectMapper objectMapper) {
        super(eventHub, objectMapper);
    }

    @Override
    protected List<TrboNetRawEvent> parseEvent(String payload) throws IOException {
        TrboNetRawEvent deviceEvent = new TrboNetRawEvent();
        deviceEvent.setEventTime(ZonedDateTime.now());
        deviceEvent.setPayload(new StringPayload(TrboNetRawEvent.PATH, payload));
        return Collections.singletonList(deviceEvent);
    }
}
