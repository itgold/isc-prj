package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.entity.ISafety;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.model.jpa.SafetyJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Repository for {@link SafetyJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface SafetyJpaRepository extends IPersistenceRepository<SafetyJpa>, SafetyJpaRepositoryCustom{

    SafetyJpa findByGuid(String guid);

    List<SafetyJpa> findByGuidIn(Set<String> guids);

    SafetyJpa findByExternalId(String externalId);

    @Override
    void deleteByGuid(String guid);

    Set<ISafety> findByRegionsIn(Set<RegionJpa> regions);

}
