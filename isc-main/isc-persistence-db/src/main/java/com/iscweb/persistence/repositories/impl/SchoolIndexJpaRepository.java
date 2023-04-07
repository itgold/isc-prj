package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.SchoolIndexJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for {@link SchoolIndexJpa} entities.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface SchoolIndexJpaRepository extends IPersistenceRepository<SchoolIndexJpa> {
}
