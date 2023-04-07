package com.iscweb.service.entity;

import com.iscweb.common.model.entity.ISchoolDistrictIndex;
import com.iscweb.persistence.model.jpa.SchoolDistrictIndexJpa;
import com.iscweb.persistence.repositories.impl.SchoolDistrictIndexJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

/**
 * Service class for school district indexes.
 */
@Slf4j
@Service
public class SchoolDistrictIndexService extends BaseJpaEntityService<SchoolDistrictIndexJpaRepository, ISchoolDistrictIndex> {

    @PreAuthorize(IS_AUTHENTICATED)
    @Transactional(transactionManager = "jpaTransactionManager", propagation = Propagation.REQUIRED)
    public void createOrUpdate(Set<SchoolDistrictIndexJpa> indexes) {
        getRepository().saveAll(indexes);
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
