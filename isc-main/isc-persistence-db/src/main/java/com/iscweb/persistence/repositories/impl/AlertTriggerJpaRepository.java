package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.AlertTriggerJpa;
import com.iscweb.persistence.model.jpa.AlertJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for{@link AlertJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface AlertTriggerJpaRepository extends IPersistenceRepository<AlertTriggerJpa>, AlertTriggerJpaRepositoryCustom {
    AlertTriggerJpa findByName(String name);

    AlertTriggerJpa findByGuid(String guid);

}
