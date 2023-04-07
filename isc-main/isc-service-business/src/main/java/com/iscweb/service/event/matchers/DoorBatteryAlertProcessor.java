package com.iscweb.service.event.matchers;

import com.iscweb.common.events.alerts.AlertMatchingContext;
import com.iscweb.common.model.alert.AlertTriggerDto;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Qualifier("DoorBatteryAlertProcessor")
@Service
public class DoorBatteryAlertProcessor extends BaseAlertProcessor {

    public static final String ALERT_TYPE = "DoorBattery";

    @Override
    public String getAlertType() {
        return DoorBatteryAlertProcessor.ALERT_TYPE;
    }

    @Override
    public List<IEvent<? extends ITypedPayload>> process(AlertTriggerDto config, AlertMatchingContext context) {
        return null;
    }
}
