package com.iscweb.common.events.alerts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DateTimeAlertTriggerMatcher extends BaseAlertTriggerMatcher {
    public DateTimeAlertTriggerMatcher(String json, ObjectMapper jsonMapper) throws JsonProcessingException {
        super(json, jsonMapper);
    }
}
