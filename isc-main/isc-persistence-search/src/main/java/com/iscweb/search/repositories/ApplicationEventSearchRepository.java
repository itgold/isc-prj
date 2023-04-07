package com.iscweb.search.repositories;

import com.iscweb.search.model.ApplicationEventVo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Event search repository.
 *
 * Implemented using Elasticsearch Spring Data repository base
 */
@Repository
public interface ApplicationEventSearchRepository extends ElasticsearchRepository<ApplicationEventVo, String>, EventSearchRepositoryCustom {

    List<ApplicationEventVo> findAllByCorrelationId(String correlationId, Pageable pageable);

    /**
     * Example of using ElasticSearch native query.
     * <code>
         curl -X GET "localhost:9200/events/_search?pretty" -H 'Content-Type: application/json' -d'
         {
             "from" : 0, "size" : 5,
             "query": {
             "bool":{"must":[{"wildcard":{"type":"events.*.*.state"}},{"terms":{"deviceId":["8419e91f-c898-44cc-a77a-f11e73c35660"]}}]}
             },
             "sort": [{"time": "desc"}]
         }
         '
     * </code>
     *
     * @param deviceIds
     * @param pageable
     * @return
     */
    @Query("{\"bool\":{\"must\":[{\"wildcard\":{\"type\":\"events.*.*.cameraStatus\"}},{\"terms\":{\"deviceId\":?0}}]}}")
    List<ApplicationEventVo> findCameraStatusEvents(List<String> deviceIds, Pageable pageable);

    /**
     * Example terms search (by deviceIds) using naming convention.
     *
     * @param type Event type
     * @param deviceIds List of device ids
     * @param pageable Pagination details
     * @return List of events matching the query
     */
    List<ApplicationEventVo> findByTypeLikeAndDeviceIdIn(String type, List<String> deviceIds, Pageable pageable);
}
