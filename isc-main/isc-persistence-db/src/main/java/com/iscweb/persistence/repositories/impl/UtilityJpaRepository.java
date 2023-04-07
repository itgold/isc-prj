package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.entity.IUtility;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.model.jpa.UtilityJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Repository for {@link UtilityJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface UtilityJpaRepository extends IPersistenceRepository<UtilityJpa>, UtilityJpaRepositoryCustom{

    UtilityJpa findByGuid(String guid);

    List<UtilityJpa> findByGuidIn(Set<String> guids);

    UtilityJpa findByExternalId(String externalId);

    @Override
    void deleteByGuid(String guid);

    Set<IUtility> findByRegionsIn(Set<RegionJpa> regions);

}
