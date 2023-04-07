package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.SchoolDistrictJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for {@link SchoolDistrictJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface SchoolDistrictJpaRepository extends IPersistenceRepository<SchoolDistrictJpa>, SchoolDistrictJpaRepositoryCustom {

    SchoolDistrictJpa findByGuid(String guid);

    SchoolDistrictJpa findByName(String name);

    @Override
    void deleteByGuid(String guid);
}
