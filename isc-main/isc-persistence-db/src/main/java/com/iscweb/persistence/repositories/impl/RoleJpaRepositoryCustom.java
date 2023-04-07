package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.entity.IDrone;
import com.iscweb.common.model.entity.IRole;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface RoleJpaRepositoryCustom {
    List<IRole> findAll(List<ProjectionDto> columns, PageRequest paging);

    Page<IRole> findEntities(QueryFilterDto filter, Pageable paging);
}
