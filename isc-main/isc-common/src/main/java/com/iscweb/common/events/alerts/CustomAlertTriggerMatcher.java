package com.iscweb.common.events.alerts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAlertTriggerMatcher extends BaseAlertTriggerMatcher {
    public CustomAlertTriggerMatcher(String json, ObjectMapper jsonMapper) throws JsonProcessingException {
        super(json, jsonMapper);
    }
}
