package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.SchoolDistrictIndexJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for {@link SchoolDistrictIndexJpa} entities.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface SchoolDistrictIndexJpaRepository extends IPersistenceRepository<SchoolDistrictIndexJpa> {
}
