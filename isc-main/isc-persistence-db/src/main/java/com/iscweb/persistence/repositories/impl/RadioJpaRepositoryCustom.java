package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.persistence.model.jpa.RadioJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Custom DAO methods for {@link RadioJpa} objects.
 */
public interface RadioJpaRepositoryCustom {
    List<RadioJpa> findAll(List<ProjectionDto> columns, PageRequest paging);

    Page<RadioJpa> findEntities(QueryFilterDto filter, Pageable paging);
}
