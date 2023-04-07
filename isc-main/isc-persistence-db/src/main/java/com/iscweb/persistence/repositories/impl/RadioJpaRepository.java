package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.RadioJpa;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Repository for {@link RadioJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface RadioJpaRepository extends IPersistenceRepository<RadioJpa>, RadioJpaRepositoryCustom {

    RadioJpa findByGuid(String guid);

    List<RadioJpa> findByGuidIn(Set<String> guids);

    RadioJpa findByExternalId(String externalId);

    @Override
    void deleteByGuid(String guid);

    Set<RadioJpa> findByRegionsIn(Set<RegionJpa> regions);
}
