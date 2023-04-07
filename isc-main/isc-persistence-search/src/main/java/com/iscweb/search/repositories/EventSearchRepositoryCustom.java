package com.iscweb.search.repositories;

import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.search.model.ApplicationEventVo;
import com.iscweb.search.model.StateEventVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

/**
 * Customized Event search repository
 *
 * Provides manually created queries into elastic search using query builders API.
 */
public interface EventSearchRepositoryCustom {
    List<StateEventVo> queryDeviceMostRecentEvents(List<Class<?>> eventClasses, List<String> deviceIds) throws IOException;

    Page<ApplicationEventVo> findAllWithFilter(QueryFilterDto filter, Pageable pageable) throws IOException;
}
