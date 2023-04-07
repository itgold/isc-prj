package com.iscweb.search.repositories;

import com.google.common.collect.Lists;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.util.EventUtils;
import com.iscweb.search.model.ApplicationEventVo;
import com.iscweb.search.model.StateEventVo;
import com.iscweb.search.utils.SearchUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.InnerHitBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import java.io.IOException;
import java.util.List;

@Slf4j
public class EventSearchRepositoryCustomImpl extends BaseCustomSearchRepository implements EventSearchRepositoryCustom {

    private static final String HITS_BY_EVENT_TYPE = "by_event_type";

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RestHighLevelClient client;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ElasticsearchConverter elasticsearchConverter;

    /**
     * Query last known device events for list of the device.
     *
     * <code>
        curl -X GET "localhost:9200/events/_search?pretty" -H 'Content-Type: application/json' -d'
        {
          "from" : 0, "size" : 5,
          "query": {
            "bool":{"must":[
                {
                    "bool": {
                           "should": [
                              {"wildcard":{"type":"events.*.*.state"}},
                              {"wildcard":{"type":"events.*.*.cameraStatus"}}
                           ],
                           "minimum_should_match": 1
                    }
                },
                {
                "terms":{
                    "deviceId":[
                        "7c7f4f27-3d7e-4182-9bc5-6258ba95e464",
                        "c40154f4-f3df-4199-88bb-c9d7f44b5d75"
                    ]}}]}
          },
          "sort": [{"time": "desc"}],
          "collapse" : {
                "field" : "deviceId",
                "inner_hits" : {
                    "name": "by_event_type",
                    "collapse" : {"field" : "type"},
                    "size": 3,
                    "sort": [{"time": "desc"}]
                }
          }
        }
        '
     * </code>
     * @param eventClasses Most recent event types to search for each device
     * @param deviceIds List of device ids
     * @return List of device events
     * @throws IOException
     */
    @Override
    public List<StateEventVo> queryDeviceMostRecentEvents(List<Class<?>> eventClasses, List<String> deviceIds) throws IOException {
        SearchRequest searchRequest = SearchUtils.createSearchRequest();
        SearchSourceBuilder searchQuery = new SearchSourceBuilder();

        BoolQueryBuilder typeQuery = QueryBuilders.boolQuery().minimumShouldMatch(1);
        for (Class<?> eventType : eventClasses) {
            typeQuery.should(QueryBuilders.wildcardQuery("type", EventUtils.generateEventPath(eventType)));
        }

        searchQuery.sort(SortBuilders.fieldSort("time").order(SortOrder.DESC));
        searchQuery.query(
                QueryBuilders.boolQuery()
                    .must(typeQuery)
                    .must(QueryBuilders.termsQuery("deviceId", deviceIds))
        );
        searchQuery.collapse(
                new CollapseBuilder("deviceId")
                .setInnerHits(
                        new InnerHitBuilder("by_event_type")
                            .setSize(eventClasses.size())
                            .addSort(SortBuilders.fieldSort("time").order(SortOrder.DESC))
                            .setInnerCollapse(new CollapseBuilder("type"))
                )
        );
        searchRequest.source(searchQuery);

        // log.debug("New ES query:\n {}", searchRequest.source().toString());
        SearchResponse response = getClient().search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits != null ? hits.getHits() : null;

        List<StateEventVo> result = Lists.newArrayList();
        if (searchHits != null && searchHits.length > 0) {
            for (SearchHit hit : hits.getHits()) {
                StateEventVo aggregatedEvents = new StateEventVo(parseVo(hit));

                SearchHits innerHits = hit.getInnerHits().get(HITS_BY_EVENT_TYPE);
                SearchHit[] events = innerHits != null ? innerHits.getHits() : null;
                if (events != null) {
                    for (SearchHit event : events) {
                        ApplicationEventVo eventVo = parseVo(event);
                        aggregatedEvents.addEvent(eventVo);
                    }
                }

                result.add(aggregatedEvents);
            }
        }

        return result;
    }

    @Override
    public Page<ApplicationEventVo> findAllWithFilter(QueryFilterDto filter, Pageable pageable) throws IOException {
        SearchRequest searchRequest = SearchUtils.createSearchRequest();
        SearchSourceBuilder searchQuery = new SearchSourceBuilder();
        searchQuery.sort(SortBuilders.fieldSort("time").order(SortOrder.DESC));

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        searchQuery.query(applyFilter(filter, queryBuilder));

        int  offset = pageable.getPageNumber() * pageable.getPageSize();
        searchQuery.from(offset);
        searchQuery.size(pageable.getPageSize());

        searchRequest.source(searchQuery);

        log.debug("New ES query:\n {}", searchRequest.source().toString());
        SearchResponse response = getClient().search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits != null ? hits.getHits() : null;

        List<ApplicationEventVo> result = Lists.newArrayList();
        if (searchHits != null && searchHits.length > 0) {
            for (SearchHit hit : hits.getHits()) {
                ApplicationEventVo eventVo = parseVo(hit);
                result.add(eventVo);
            }
        }

        long totalHits = hits.getTotalHits() != null ? hits.getTotalHits().value : 0L;
        return new PageImpl<>(result, pageable, totalHits);
    }
}
