package com.iscweb.service.event.matchers;

import com.google.common.collect.Lists;
import com.iscweb.common.events.alerts.AlertMatchingContext;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.alert.AlertTriggerDto;
import com.iscweb.common.model.dto.entity.core.AlertDto;
import com.iscweb.common.model.event.AlertEvent;
import com.iscweb.common.model.event.DeviceIncrementalUpdatePayload;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Door Left Opened (DLO) event alert trigger processor implementation.
 */
@Slf4j
@Qualifier("DoorDloAlertProcessor")
@Service
public class DoorDloAlertProcessor extends BaseAlertProcessor {

    public static final String ALERT_TYPE = "DoorDLO";

    @Override
    public String getAlertType() {
        return DoorDloAlertProcessor.ALERT_TYPE;
    }

    private static final List<String> DLO_CODES = Lists.newArrayList("DOOR_LEFT_OPENED_DLO");
    private static final List<String> DLO_END_CODES = Lists.newArrayList("END_OF_DLO_DOOR_LEFT_OPENED");

    @Override
    public List<IEvent<? extends ITypedPayload>> process(AlertTriggerDto trigger, AlertMatchingContext context) {
        List<IEvent<? extends ITypedPayload>> result = null;

        if (context.getEvent() != null && context.getEvent().getPayload() instanceof DeviceIncrementalUpdatePayload) {
            DeviceIncrementalUpdatePayload<?> payload = (DeviceIncrementalUpdatePayload<?>) context.getEvent().getPayload();
            if (DLO_CODES.contains(payload.getCode())) {
                try {
                    AlertDto alert = generateAlert(trigger.getId(), payload.getDeviceId(), EntityType.valueOf(payload.getType()),  context);
                    AlertEvent event = createAlertEvent(alert, context.getEvent(), payload);
                    result = Lists.newArrayList(event);
                } catch (Exception e) {
                    log.error("Unable to create alert for door DLO event", e);
                }

            } else if (DLO_END_CODES.contains(payload.getCode())) {
                AlertDto alert = getAlertService().findAlert(trigger.getId(), payload.getDeviceId(), EntityType.valueOf(payload.getType()));
                if (alert != null) {
                    getAlertService().delete(alert.getId());
                    // TODO: notify about change to the UI to refresh the list of alerts?
                }
            }
        }

        return result;
    }
}
