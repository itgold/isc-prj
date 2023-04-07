package com.iscweb.search.repositories;

import com.iscweb.search.model.RawEventVo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Event search repository.
 *
 * Implemented using Elasticsearch Spring Data repository base
 */
@Repository
public interface RawEventSearchRepository extends ElasticsearchRepository<RawEventVo, String> {
}
