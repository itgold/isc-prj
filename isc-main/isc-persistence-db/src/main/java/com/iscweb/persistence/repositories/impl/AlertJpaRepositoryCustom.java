package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.entity.IAlert;
import com.iscweb.persistence.model.jpa.AlertJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for{@link AlertJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface AlertJpaRepositoryCustom {

  Page<IAlert> findEntities(QueryFilterDto filter, Pageable paging);
}
