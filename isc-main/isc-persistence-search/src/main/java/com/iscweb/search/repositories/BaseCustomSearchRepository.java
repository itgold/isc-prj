package com.iscweb.search.repositories;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.iscweb.common.events.payload.DeviceStatePayload;
import com.iscweb.common.model.ISearchEntityVo;
import com.iscweb.common.model.dto.ColumnFilterDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.util.DateUtils;
import com.iscweb.common.util.StringUtils;
import com.iscweb.search.model.ApplicationEventVo;
import com.iscweb.search.model.DeviceStateVo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.document.DocumentAdapters;
import org.springframework.data.elasticsearch.core.document.SearchDocument;
import org.springframework.util.CollectionUtils;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

public abstract class BaseCustomSearchRepository {

    private static final String WILDCARD = "*";

    private static final List<String> WILDCARD_FIELDS = Lists.newArrayList("payload.code", "payload.type", "payload.description");
    private static final String FIELD_SCHOOL = "schoolId";
    private static final String FIELD_TIME = "eventTime";
    private static final String FIELD_CORRELATION_ID = "correlationId";
    private static final String FIELD_EVENT_ID = "eventId";
    private static final String FIELD_DEVICE_ID = "deviceId";

    public abstract ElasticsearchConverter getElasticsearchConverter();

    protected ApplicationEventVo parseVo(SearchHit hit) {
        ApplicationEventVo result;
        SearchDocument searchDocument = DocumentAdapters.from(hit);
        if (DeviceStateVo.INDEX.equalsIgnoreCase(hit.getIndex())) {
            DeviceStateVo stateVo = getElasticsearchConverter().read(DeviceStateVo.class, searchDocument);
            ApplicationEventVo appEvent = new ApplicationEventVo(ApplicationEventVo.TYPE);
            appEvent.setDeviceId(stateVo.getDeviceId());
            appEvent.setType("New State");
            appEvent.setEventId(stateVo.getEventId());
            appEvent.setCorrelationId(stateVo.getCorrelationId());
            appEvent.setReferenceId(stateVo.getReferenceId());
            appEvent.setTime(stateVo.getTime());
            appEvent.setOrigin(stateVo.getOrigin());
            appEvent.setSchoolId(stateVo.getSchoolId());
            appEvent.setDistrictId(stateVo.getDistrictId());
            appEvent.setPayload(new DeviceStatePayload(null, appEvent.getDeviceId(), Sets.newHashSet(stateVo.getState())));
            result = appEvent;
        } else {
            result = getElasticsearchConverter().read(ApplicationEventVo.class, searchDocument);
        }
        return result;
    }

    protected BoolQueryBuilder applyFilter(QueryFilterDto filter, BoolQueryBuilder queryBuilder) {
        if (!CollectionUtils.isEmpty(filter.getColumns())) {
            filter.getColumns().forEach(columnFilter -> {
                QueryBuilder query = createFieldQuery(columnFilter);
                if (query != null) {
                    queryBuilder.must(query);
                }
            });
        }

        return queryBuilder;
    }

    protected QueryBuilder createFieldQuery(ColumnFilterDto columnFilter) {
        QueryBuilder result = null;
        String value = columnFilter.getValue();
        if (!StringUtils.isBlank(value)) {
            if (WILDCARD_FIELDS.contains(columnFilter.getKey())) {
                if (!value.contains(WILDCARD)) {
                    value = WILDCARD + value + WILDCARD;
                }
                result = QueryBuilders.wildcardQuery(columnFilter.getKey(), value);
            } else if (FIELD_SCHOOL.equalsIgnoreCase(columnFilter.getKey())) {
                result = QueryBuilders.boolQuery()
                        .should(QueryBuilders.matchQuery(columnFilter.getKey(), value))
                        .should(QueryBuilders.matchQuery(columnFilter.getKey(), ISearchEntityVo.SEARCH_FIELD_ANY));
            } else if (FIELD_TIME.equalsIgnoreCase(columnFilter.getKey())) {
                ZonedDateTime startTime = null;
                ZonedDateTime endTime = null;

                String[] dates = value.split("\\.\\.");
                if (dates.length > 0 && dates.length <= 2) {
                    startTime = DateUtils.parseAsZonedDateTime(dates[0].trim(), null);
                    endTime = dates.length > 1 ? DateUtils.parseAsZonedDateTime(dates[1].trim(), null) : null;
                    if (startTime != null || endTime != null) {
                        if (startTime == null || endTime == null) {
                            ZonedDateTime dateTime = startTime != null ? startTime : endTime;
                            startTime = dateTime.toLocalDate().atStartOfDay(dateTime.getZone());
                            endTime = dateTime.with(LocalTime.MAX);
                        }
                    }
                }

                if (startTime == null) { // not resolved
                    startTime = DateUtils.DAY_IN_FUTURE;
                    endTime = DateUtils.DAY_IN_FUTURE;
                }

                result = QueryBuilders.rangeQuery("time").gte(startTime).lte(endTime);
            } else if (FIELD_CORRELATION_ID.equalsIgnoreCase(columnFilter.getKey()) || FIELD_DEVICE_ID.equalsIgnoreCase(columnFilter.getKey())) {
                result = QueryBuilders.boolQuery()
                        .should(QueryBuilders.matchQuery(columnFilter.getKey(), value));
            } else {
                result = QueryBuilders.termQuery(columnFilter.getKey(), value);
            }
        }

        return result;
    }
}
