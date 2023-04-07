package com.iscweb.service.event.matchers;

import com.iscweb.common.events.alerts.AlertMatchingContext;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.ISearchEntityVo;
import com.iscweb.common.model.dto.entity.core.AlertDto;
import com.iscweb.common.model.dto.entity.core.SchoolDto;
import com.iscweb.common.model.event.AlertEvent;
import com.iscweb.common.model.event.DeviceIncrementalUpdatePayload;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.model.metadata.AlertStatus;
import com.iscweb.common.service.IAlertTriggerProcessor;
import com.iscweb.service.AlertService;
import com.iscweb.service.composite.impl.CompositeHelperService;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;

/**
 * Base implementation for all alert trigger processors.
 */
public abstract class BaseAlertProcessor implements IAlertTriggerProcessor {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private AlertService alertService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CompositeHelperService compositeHelperService;

    protected AlertDto generateAlert(String triggerId, String deviceId, EntityType deviceType, AlertMatchingContext context) throws ServiceException {
        AlertDto alert = getAlertService().findAlert(triggerId, deviceId, deviceType);
        if (alert == null) {
            alert = new AlertDto();
            alert.setTriggerId(triggerId);
            alert.setDeviceId(deviceId);
            alert.setDeviceType(deviceType);
            alert.setStatus(AlertStatus.ACTIVE);
        }

        alert.incrementCount();
        alert.setEventId(context.getEvent().getEventId());
        if (context.getEvent().getPayload() instanceof DeviceIncrementalUpdatePayload) {
            DeviceIncrementalUpdatePayload<?> payload = (DeviceIncrementalUpdatePayload<?>) context.getEvent().getPayload();
            alert.setCode(payload.getCode());
            alert.setDescription(payload.getDescription());
        }

        if (!ObjectUtils.isEmpty(deviceId)) {
            SchoolDto school = getCompositeHelperService().findSchool(deviceId);
            if (school != null) {
                alert.setSchoolId(school.getId());
                if (school.getSchoolDistrict() != null) {
                    alert.setDistrictId(school.getSchoolDistrict().getId());
                }
            }
        } else {
            alert.setSchoolId(ISearchEntityVo.SEARCH_FIELD_ANY);
            alert.setDistrictId(ISearchEntityVo.SEARCH_FIELD_ANY);
        }

        return alert.getCount() == 1 ? getAlertService().create(alert) : getAlertService().update(alert);
    }

    protected AlertEvent createAlertEvent(AlertDto alert, IEvent<ITypedPayload> event, DeviceIncrementalUpdatePayload<?> eventPayload) {
        AlertEvent alertEvent = new AlertEvent();
        alertEvent.setEventId(alert.getId());
        alertEvent.setEventTime(ZonedDateTime.now());
        alertEvent.setReceivedTime(event.getEventTime());
        alertEvent.setReferenceId(event.getEventId());
        alertEvent.setCorrelationId(event.getCorrelationId());
        alertEvent.setPayload(new AlertEvent.AlertEventPayload(alert));
        return alertEvent;
    }
}
