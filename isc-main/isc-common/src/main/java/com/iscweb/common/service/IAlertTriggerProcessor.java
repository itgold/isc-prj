package com.iscweb.common.service;

import com.iscweb.common.events.alerts.AlertMatchingContext;
import com.iscweb.common.model.alert.AlertTriggerDto;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;

import java.util.List;

/**
 * Marker interface for all alert trigger processor implementations.
 */
public interface IAlertTriggerProcessor {
    String getAlertType();

    List<IEvent<? extends ITypedPayload>> process(AlertTriggerDto config, AlertMatchingContext context);
}
