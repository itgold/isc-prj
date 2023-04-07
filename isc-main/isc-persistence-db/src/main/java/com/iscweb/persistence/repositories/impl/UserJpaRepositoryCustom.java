package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.entity.IUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserJpaRepositoryCustom {
  /**
   * Find all entities using projection (subset of columns).
   *
   * @param columns List of columns to query
   * @param paging Pagination details
   * @return
   */
  List<IUser> findAll(List<ProjectionDto> columns, PageRequest paging);

  /**
   * Search for records with given filter and pagination.
   *
   * @param filter Search filter
   * @param paging Pagination details
   * @return
   */
  Page<IUser> findEntities(QueryFilterDto filter, Pageable paging);
}
