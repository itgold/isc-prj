package com.iscweb.component.web.controller.graphql.queries;

import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.AlertSearchResultDto;
import com.iscweb.common.model.dto.entity.core.AlertTriggerSearchResultDto;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.model.dto.entity.core.IncrementalEventsSearchResultDto;
import com.iscweb.common.model.dto.entity.core.TagDto;
import com.iscweb.common.model.event.DeviceIncrementalUpdatePayload;
import com.iscweb.component.web.controller.graphql.common.PageRequestDto;
import com.iscweb.component.web.controller.graphql.common.SortOrderDto;
import com.iscweb.service.AlertService;
import com.iscweb.service.AlertTriggerService;
import com.iscweb.service.search.EventsHistorySearchService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.iscweb.component.web.util.GraphQlUtils.sortingPage;

/**
 * GraphQL query resolver for tags.
 */
@Slf4j
@Component
public class GraphQlEventsQuery implements GraphQLQueryResolver {

    private static final PageRequest DEFAULT_PAGE = PageRequest.of(0, 10, Sort.unsorted());

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private EventsHistorySearchService eventsHistorySearchService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private AlertTriggerService alertTriggerService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private AlertService alertService;

    public IncrementalEventsSearchResultDto events(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return getEventsHistorySearchService().findAllIncrementalUpdates(filter, page.toPageRequest(sort));
    }

    public AlertTriggerSearchResultDto queryAlertTriggers(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return (AlertTriggerSearchResultDto) getAlertTriggerService().findAlertTriggers(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }

    public AlertSearchResultDto queryAlerts(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return getAlertService().findAlerts(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }
}
