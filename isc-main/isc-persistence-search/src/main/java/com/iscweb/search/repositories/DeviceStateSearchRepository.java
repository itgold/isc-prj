package com.iscweb.search.repositories;

import com.iscweb.search.model.DeviceStateVo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Event search repository.
 *
 * Implemented using Elasticsearch Spring Data repository base
 */
@Repository
public interface DeviceStateSearchRepository extends ElasticsearchRepository<DeviceStateVo, String> {
}
