package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.entity.IExternalUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExternalUserJpaRepositoryCustom {
    /**
     * Search for records with given filter and pagination.
     *
     * @param filter Search filter
     * @param paging Pagination details
     * @return
     */
    Page<IExternalUser> findEntities(QueryFilterDto filter, Pageable paging);
}
