package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.ExternalUserJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for {@link ExternalUserJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface ExternalUserJpaRepository extends IPersistenceRepository<ExternalUserJpa>, ExternalUserJpaRepositoryCustom {

    ExternalUserJpa findByGuid(String guid);

    ExternalUserJpa findByExternalId(String externalId);

}
