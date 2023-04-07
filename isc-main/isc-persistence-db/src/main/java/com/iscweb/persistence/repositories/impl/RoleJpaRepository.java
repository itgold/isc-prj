package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.RoleJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for {@link RoleJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface RoleJpaRepository extends IPersistenceRepository<RoleJpa>, RoleJpaRepositoryCustom {

    RoleJpa findByGuid(String guid);

    RoleJpa findByName(String name);
}
