package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.entity.IAlertTrigger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlertTriggerJpaRepositoryCustom {
    Page<IAlertTrigger> findEntities(QueryFilterDto filter, Pageable paging);
}
