package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.entity.IDoor;
import com.iscweb.persistence.model.jpa.DoorJpa;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Repository for {@link DoorJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface DoorJpaRepository extends IPersistenceRepository<DoorJpa>, DoorJpaRepositoryCustom {

    DoorJpa findByGuid(String guid);

    List<DoorJpa> findByGuidIn(Set<String> guids);

    DoorJpa findByExternalId(String externalId);

    @Override
    void deleteByGuid(String guid);

    Set<IDoor> findByRegionsIn(Set<RegionJpa> regions);
}
