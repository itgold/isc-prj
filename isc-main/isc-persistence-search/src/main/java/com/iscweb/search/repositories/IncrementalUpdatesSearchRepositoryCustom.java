package com.iscweb.search.repositories;

import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.search.model.ApplicationEventVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface IncrementalUpdatesSearchRepositoryCustom {
    Page<ApplicationEventVo> findAllWithFilter(QueryFilterDto filter, Pageable pageable) throws IOException;
}
