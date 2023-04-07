package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.entity.IDoor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoorJpaRepositoryCustom {
    List<IDoor> findAll(List<ProjectionDto> columns, PageRequest paging);

    Page<IDoor> findEntities(QueryFilterDto filter, Pageable paging);
}
