package com.iscweb.integration.doors.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iscweb.common.events.payload.StringPayload;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.sis.impl.BaseSisEventListeningService;
import com.iscweb.integration.doors.events.SaltoRawEvent;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Salto implementation for event processing.
 *
 * Note: Salto raw events are simple JSON payloads.
 * We are not parsing Salto events at this level. Just wrap them up as device events with raw payload
 * and pass for further processing by the system
 */
public class SaltoEventListeningService extends BaseSisEventListeningService<SaltoRawEvent, StringPayload, String> {

    public SaltoEventListeningService(IEventHub eventHub, ObjectMapper objectMapper) {
        super(eventHub, objectMapper);
    }

    @Override
    protected List<SaltoRawEvent> parseEvent(String payload) throws IOException {
        SaltoRawEvent deviceEvent = new SaltoRawEvent();
        deviceEvent.setEventTime(ZonedDateTime.now());
        deviceEvent.setPayload(new StringPayload(SaltoRawEvent.PATH, payload));
        return Collections.singletonList(deviceEvent);
    }
}
