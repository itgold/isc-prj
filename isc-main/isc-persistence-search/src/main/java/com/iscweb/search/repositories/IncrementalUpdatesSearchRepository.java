package com.iscweb.search.repositories;

import com.iscweb.search.model.IncrementUpdateEventVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Event search repository.
 *
 * Implemented using Elasticsearch Spring Data repository base
 */
@Repository
public interface IncrementalUpdatesSearchRepository extends ElasticsearchRepository<IncrementUpdateEventVo, String>, IncrementalUpdatesSearchRepositoryCustom {
    Page<IncrementUpdateEventVo> findAll(Pageable pageable);
}
