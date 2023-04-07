package com.iscweb.service;

import com.iscweb.common.events.BaseEvent;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.AlertActionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.AlertDto;
import com.iscweb.common.model.dto.entity.core.AlertSearchResultDto;
import com.iscweb.common.model.event.AlertAckEvent;
import com.iscweb.common.model.event.AlertIgnoreEvent;
import com.iscweb.common.model.event.BaseAlertEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.model.metadata.AlertStatus;
import com.iscweb.common.service.IEventHub;
import com.iscweb.persistence.model.jpa.AlertJpa;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.AlertEntityService;
import com.iscweb.service.search.EventsHistorySearchService;
import com.iscweb.service.security.IscPrincipal;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

@Slf4j
@Service
public class AlertService extends BaseBusinessService<AlertDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private AlertEntityService entityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IEventHub eventHub;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private EventsHistorySearchService eventsSearch;

    @PreAuthorize(IS_AUTHENTICATED)
    public List<AlertDto> findAll(PageRequest paging) {
        return getEntityService().findAll(paging)
                                 .stream()
                                 .map(jpa -> (AlertDto) Convert.my(jpa)
                                                              .scope(Scope.ALL)
                                                              .boom())
                                 .collect(Collectors.toList());
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public AlertDto findAlert(String triggerId, String deviceId, EntityType deviceType) {
        return getEntityService().findAlert(triggerId, deviceId, deviceType);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public AlertSearchResultDto findAlerts(QueryFilterDto filter, PageRequest pageRequest) {
        return (AlertSearchResultDto) getEntityService().findAlerts(filter, pageRequest);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public AlertDto findByGuid(String alertId) {
        AlertJpa alert = getEntityService().findByGuid(alertId);
        return alert != null ? (AlertDto) Convert.my(alert)
                .scope(Scope.ALL)
                .boom() : null;
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public AlertDto updateAlert(AlertActionDto action, IscPrincipal principal) throws ServiceException {
        AlertDto result = action.getAlertId() != null ? findByGuid(action.getAlertId()) : null;

        if (result != null) {
            if (action.getAlertStatus() != null) {
                result.setStatus(action.getAlertStatus());
            }
            if (action.getAlertSeverity() != null) {
                result.setSeverity(action.getAlertSeverity());
            }

            if (action.getAlertStatus() == AlertStatus.ACKED || action.getAlertStatus() == AlertStatus.IGNORE) {
                BaseAlertEvent<?, ?> event;
                if (action.getAlertStatus() == AlertStatus.ACKED) {
                    event = new AlertAckEvent();
                    ((AlertAckEvent) event).setPayload(new AlertAckEvent.AlertAckEventPayload(result, action.getNotes(), principal.getUsername()));
                } else {
                    event = new AlertIgnoreEvent();
                    ((AlertIgnoreEvent) event).setPayload(new AlertIgnoreEvent.AlertIgnoreEventPayload(result, action.getNotes(), principal.getUsername()));
                }

                event.setEventTime(ZonedDateTime.now());
                event.setReferenceId(result.getId()); // original alert event has the same id as the alert itself
                event.setReceivedTime(ZonedDateTime.now());

                // original alert event has the same id as the alert itself
                BaseEvent<ITypedPayload> originalEvent = getEventsSearch().findById(result.getId());
                if (originalEvent != null) {
                    event.setCorrelationId(originalEvent.getCorrelationId());
                    event.setEventTime(originalEvent.getEventTime());
                }

                getEventHub().post(event);
                getEntityService().delete(result.getId());
            } else {
                result = super.update(result);
            }
        }

        return result;
    }
}
