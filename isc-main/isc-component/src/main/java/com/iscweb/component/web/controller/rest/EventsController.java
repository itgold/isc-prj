package com.iscweb.component.web.controller.rest;

import com.google.common.collect.Lists;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.alert.AlertTriggerDto;
import com.iscweb.common.model.alert.AlertTriggerMatcherDto;
import com.iscweb.common.model.alert.AlertTriggerMatcherType;
import com.iscweb.common.model.dto.ColumnFilterDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.ApplicationEventsSearchResultDto;
import com.iscweb.common.model.dto.response.StringResponseDto;
import com.iscweb.common.model.event.ApplicationEventDto;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.security.ApplicationSecurity;
import com.iscweb.common.security.ISystemUser;
import com.iscweb.common.service.IAlertTriggerProcessor;
import com.iscweb.component.web.controller.BaseInternalApiController;
import com.iscweb.service.AlertTriggerService;
import com.iscweb.service.event.matchers.DoorDloAlertProcessor;
import com.iscweb.service.event.pipeline.AlertManagerService;
import com.iscweb.service.search.EventsHistorySearchService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

@Slf4j
@RestController
@RequestMapping("/rest/events")
@PreAuthorize(IS_AUTHENTICATED)
public class EventsController extends BaseInternalApiController<EventsHistorySearchService> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private AlertManagerService alertManagerService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private AlertTriggerService alertTriggerService;

    @GetMapping(value = "/relatedEvents/{correlationId}")
    public List<ApplicationEventDto<ITypedPayload>> relatedEvents(@PathVariable(value = "correlationId") String correlationId) {
        QueryFilterDto filter = new QueryFilterDto();
        List<ColumnFilterDto> columns = Lists.newArrayList(new ColumnFilterDto("correlationId", correlationId));
        filter.setColumns(columns);

        ApplicationEventsSearchResultDto result = getService().findEvents(filter, PageRequest.of(0, 1000, Sort.unsorted()));
        return result.getData().stream().collect(Collectors.toList());
    }

    @GetMapping(value = "/alertTriggerProcessors")
    public List<String> alertTriggerProcessors() {
        List<IAlertTriggerProcessor> processors = getAlertManagerService().listAlertTriggerProcessors();
        return processors.stream().map(processor -> processor.getAlertType()).collect(Collectors.toList());
    }

    // curl -X POST http://localhost:9090/rest/events/alertConfigs
    @RequestMapping(value = "/alertConfigs", method = RequestMethod.POST)
    public StringResponseDto<Void> generateAlertRules(AlertTriggerDto alertConfig) throws ServiceException {
        try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
            AlertTriggerDto config = getAlertTriggerService().findByName("TestConfig1");
            if (config == null) {
                config = new AlertTriggerDto();
                config.setName("TestConfig1");
                config.setProcessorType(DoorDloAlertProcessor.ALERT_TYPE);
                config.setActive(true);
                AlertTriggerMatcherDto matcher = new AlertTriggerMatcherDto();
                matcher.setType(AlertTriggerMatcherType.DATE_TIME);
                matcher.setBody("{\"and\": { \"items\": [ { \"property\": \"dateTime.date\", \"operator\": \">\", \"value\": \"2021-05-01\" },{ \"property\": \"dateTime.date\", \"operator\": \"<\", \"value\": \"2025-05-30\" } ]}}");
                config.setMatchers(Lists.newArrayList(matcher));

                getAlertTriggerService().create(config);
            }
        }

        return StringResponseDto.of("Event Generated!");
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
