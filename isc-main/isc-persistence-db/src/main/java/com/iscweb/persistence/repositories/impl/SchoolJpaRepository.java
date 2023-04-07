package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.SchoolDistrictJpa;
import com.iscweb.persistence.model.jpa.SchoolJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for {@link SchoolDistrictJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface SchoolJpaRepository extends IPersistenceRepository<SchoolJpa>, SchoolJpaRepositoryCustom {

    SchoolJpa findByGuid(String guid);

    @Override
    void deleteByGuid(String guid);

    SchoolJpa findByRegionGuid(String regionGuid);
}
