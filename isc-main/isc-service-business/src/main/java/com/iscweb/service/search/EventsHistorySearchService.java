package com.iscweb.service.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iscweb.common.events.BaseEvent;
import com.iscweb.common.events.payload.DeviceStatePayload;
import com.iscweb.common.model.IApplicationComponent;
import com.iscweb.common.model.ISearchEntityVo;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.ApplicationEventsSearchResultDto;
import com.iscweb.common.model.dto.entity.core.IncrementalEventsSearchResultDto;
import com.iscweb.common.model.dto.entity.core.SchoolDto;
import com.iscweb.common.model.event.ApplicationEventDto;
import com.iscweb.common.model.event.IApplicationEvent;
import com.iscweb.common.model.event.IDeviceEvent;
import com.iscweb.common.model.event.IExternalEntityEvent;
import com.iscweb.common.model.event.IExternalEvent;
import com.iscweb.common.model.event.IIncrementalUpdateEvent;
import com.iscweb.common.model.event.IStateEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.model.event.IncrementalUpdateEventDto;
import com.iscweb.common.model.event.camera.CameraStateEvent;
import com.iscweb.common.model.event.camera.CameraStatusEvent;
import com.iscweb.common.model.event.door.DoorStateEvent;
import com.iscweb.common.service.IApplicationSecuredService;
import com.iscweb.search.model.ApplicationEventVo;
import com.iscweb.search.model.DeviceStateVo;
import com.iscweb.search.model.IncrementUpdateEventVo;
import com.iscweb.search.model.RawEventVo;
import com.iscweb.search.model.StateEventVo;
import com.iscweb.search.repositories.ApplicationEventSearchRepository;
import com.iscweb.search.repositories.DeviceStateSearchRepository;
import com.iscweb.search.repositories.IncrementalUpdatesSearchRepository;
import com.iscweb.search.repositories.RawEventSearchRepository;
import com.iscweb.service.composite.impl.CompositeHelperService;
import com.iscweb.service.event.EventHelperService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;


/**
 * Manage system events history.
 */
@Slf4j
@Service
@PreAuthorize(IS_AUTHENTICATED)
public class EventsHistorySearchService implements IApplicationSecuredService, IApplicationComponent {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ApplicationEventSearchRepository eventRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DeviceStateSearchRepository deviceStateSearchRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IncrementalUpdatesSearchRepository incrementalUpdatesSearchRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RawEventSearchRepository rawEventSearchRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ObjectMapper jsonMapper;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private EventHelperService eventHelperService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CompositeHelperService compositeHelperService;

    public void recordApplicationEvent(IApplicationEvent<?> event) {
        boolean incremental = event instanceof IIncrementalUpdateEvent;
        ApplicationEventVo applicationEvent = incremental ? new IncrementUpdateEventVo() : new ApplicationEventVo(ApplicationEventVo.TYPE);
        applicationEvent.setEventId(event.getEventId());
        applicationEvent.setType(event.getEventPath());
        applicationEvent.setTime(ZonedDateTime.now());
        applicationEvent.setCorrelationId(event.getCorrelationId());
        applicationEvent.setReferenceId(event.getReferenceId());
        applicationEvent.setPayload(event.getPayload());
        applicationEvent.setReceivedTime(event.getReceivedTime());
        applicationEvent.setOrigin(event.getOrigin());

        if (event instanceof IDeviceEvent) {
            applicationEvent.setDeviceId(((IDeviceEvent<?>) event).getDeviceId());
        }

        if (!ObjectUtils.isEmpty(applicationEvent.getDeviceId())) {
            SchoolDto school = getCompositeHelperService().findSchool(applicationEvent.getDeviceId());
            if (school != null) {
                applicationEvent.setSchoolId(school.getId());
                if (school.getSchoolDistrict() != null) {
                    applicationEvent.setDistrictId(school.getSchoolDistrict().getId());
                }
            }
        } else {
            applicationEvent.setSchoolId(ISearchEntityVo.SEARCH_FIELD_ANY);
            applicationEvent.setDistrictId(ISearchEntityVo.SEARCH_FIELD_ANY);
        }

        if (incremental) {
            getIncrementalUpdatesSearchRepository().save((IncrementUpdateEventVo) applicationEvent);
        } else {
            getEventRepository().save(applicationEvent);
        }
    }

    public void recordRawEvent(IExternalEntityEvent<?> event) {
        RawEventVo eventVo = new RawEventVo();
        eventVo.setEventId(event.getEventId());
        eventVo.setExternalEntityId(event.getExternalEntityId());
        eventVo.setType(event.getEventPath());
        eventVo.setTime(ZonedDateTime.now());
        eventVo.setCorrelationId(event.getCorrelationId());
        eventVo.setReferenceId(event.getReferenceId());
        eventVo.setPayload(event.getPayload());
        eventVo.setReceivedTime(event.getReceivedTime());
        getRawEventSearchRepository().save(eventVo);
    }

    public void recordRawEvent(IExternalEvent<?> event) {
        RawEventVo eventVo = new RawEventVo();
        eventVo.setEventId(event.getEventId());
        eventVo.setType(event.getEventPath());
        eventVo.setTime(ZonedDateTime.now());
        eventVo.setCorrelationId(event.getCorrelationId());
        eventVo.setReferenceId(event.getReferenceId());
        eventVo.setPayload(event.getPayload());
        eventVo.setReceivedTime(event.getReceivedTime());
        getRawEventSearchRepository().save(eventVo);
    }

    public void recordDeviceState(IStateEvent<DeviceStatePayload> stateEvent) {
        DeviceStateVo eventVo = new DeviceStateVo();
        eventVo.setEventId(UUID.randomUUID().toString());
        eventVo.setDeviceId(stateEvent.getPayload().getDeviceId());
        eventVo.setType(stateEvent.getPayload().getType());
        eventVo.setTime(ZonedDateTime.now());
        eventVo.setState(stateEvent.getPayload().getState());
        eventVo.setCorrelationId(stateEvent.getCorrelationId());
        eventVo.setReferenceId(stateEvent.getEventId());

        if (!ObjectUtils.isEmpty(eventVo.getDeviceId())) {
            SchoolDto school = getCompositeHelperService().findSchool(eventVo.getDeviceId());
            if (school != null) {
                eventVo.setSchoolId(school.getId());
                if (school.getSchoolDistrict() != null) {
                    eventVo.setDistrictId(school.getSchoolDistrict().getId());
                }
            } else {
                eventVo.setSchoolId(ISearchEntityVo.SEARCH_FIELD_ANY);
                eventVo.setDistrictId(ISearchEntityVo.SEARCH_FIELD_ANY);
            }
        }

        getDeviceStateSearchRepository().save(eventVo);
    }

    @SuppressWarnings("unchecked")
    public <E extends BaseEvent<T>, T extends ITypedPayload> List<E> findAll(Pageable pageable) {
        return getEventRepository()
                .findAll(pageable)
                .stream()
                .map(recordedEvent -> (E) convert(recordedEvent))
                .collect(Collectors.toList());
    }

    public <E extends BaseEvent<T>, T extends ITypedPayload> E findById(String eventId) {
        E event = null;
        ApplicationEventVo eventVo = getEventRepository().findById(eventId).orElse(null);
        if (eventVo != null) {
            event = (E) convert(eventVo);
        }

        return event;
    }

    @SuppressWarnings("unchecked")
    public <E extends BaseEvent<T>, T extends ITypedPayload> List<E> findAllByCorrelationId(String correlationId, Pageable pageable) {
        return getEventRepository()
                .findAllByCorrelationId(correlationId, pageable)
                .stream()
                .map(recordedEvent -> (E) convert(recordedEvent))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <E extends BaseEvent<T>, T extends ITypedPayload> List<E> findCameraStatusEvents(List<String> deviceIds, Pageable pageable) {
        List<String> ids = !CollectionUtils.isEmpty(deviceIds) ?
                deviceIds.stream().map(deviceId -> String.format("\"%s\"", deviceId)).collect(Collectors.toList())
                : Collections.emptyList();
        return getEventRepository()
                .findCameraStatusEvents(ids, pageable)
                .stream()
                .map(recordedEvent -> (E) convert(recordedEvent))
                .collect(Collectors.toList());
    }

    public List<StateEventVo> findDeviceStateEvents(List<String> deviceIds) throws IOException {
        return getEventRepository().queryDeviceMostRecentEvents(Arrays.asList(new Class<?>[]{
                CameraStateEvent.class, DoorStateEvent.class, CameraStatusEvent.class
        }), deviceIds);
    }

    @SuppressWarnings("unchecked")
    private <E extends BaseEvent<T>, T extends ITypedPayload> BaseEvent<T> convert(ApplicationEventVo event) {
        E result;

        try {
            result = getEventHelperService().createEventInstance(event.getType());
            if (result instanceof IDeviceEvent) {
                ((IDeviceEvent<?>) result).setDeviceId(event.getDeviceId());
            }
            result.setCorrelationId(event.getCorrelationId());
            result.setReferenceId(event.getEventId());
            result.setPayload((T) event.getPayload());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new IllegalArgumentException("Unable to convert Event class", e);
        }

        return result;
    }

    public ApplicationEventsSearchResultDto findEvents(QueryFilterDto filter, Pageable pageable) {
        Page<ApplicationEventVo> page = new PageImpl(Collections.emptyList(), pageable, 0);

        try {
            page = getEventRepository().findAllWithFilter(filter, pageable);
        } catch (IOException e) {
            log.error("Unable to search for incremental events", e);
        }

        return ApplicationEventsSearchResultDto.builder()
                .numberOfItems((int) page.getTotalElements())
                .numberOfPages(page.getTotalPages())
                .data(page.getContent()
                        .stream()
                        .map(eventVo -> convert(eventVo, new ApplicationEventsSearchResultDto.ApplicationEventSearchResultEventDto()))
                        .collect(Collectors.toList()))
                .build();
    }

    @SuppressWarnings("unchecked")
    public IncrementalEventsSearchResultDto findAllIncrementalUpdates(QueryFilterDto filter, Pageable pageable) {
        Page<ApplicationEventVo> page = new PageImpl(Collections.emptyList(), pageable, 0);

        try {
            page = getIncrementalUpdatesSearchRepository().findAllWithFilter(filter, pageable);
        } catch (IOException e) {
            log.error("Unable to search for incremental events", e);
        }

        return IncrementalEventsSearchResultDto.builder()
                .numberOfItems((int) page.getTotalElements())
                .numberOfPages(page.getTotalPages())
                .data(page.getContent()
                        .stream()
                        .map(eventVo -> convert(eventVo, new IncrementalUpdateEventDto()))
                        .collect(Collectors.toList()))
                .build();
    }

    private <T extends ApplicationEventDto<P>, P extends ITypedPayload> T convert(ApplicationEventVo vo, T dto) {
        dto.setEventId(vo.getEventId());
        dto.setCorrelationId(vo.getCorrelationId());
        dto.setReferenceId(vo.getReferenceId());
        dto.setEventTime(vo.getTime());
        dto.setReceivedTime(vo.getReceivedTime());
        dto.setDeviceId(vo.getDeviceId());
        dto.setPayload((P) vo.getPayload());
        dto.setType(vo.getType());

        dto.setOrigin(vo.getOrigin());
        dto.setSchoolId(vo.getSchoolId());
        dto.setDistrictId(vo.getDistrictId());

        return dto;
    }

    @Override
    public Logger getLogger() {
        return log;
    }

}
