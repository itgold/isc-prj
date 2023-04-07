package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.IndexJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository to access {@link IndexJpa} entities.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface IndexJpaRepository extends IPersistenceRepository<IndexJpa> {

    IndexJpa findByGuid(String guid);
}
