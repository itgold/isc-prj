package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.entity.IDrone;
import com.iscweb.persistence.model.jpa.DroneJpa;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Repository for {@link DroneJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface DroneJpaRepository extends IPersistenceRepository<DroneJpa>, DroneJpaRepositoryCustom {

    DroneJpa findByGuid(String guid);

    DroneJpa findByExternalId(String externalId);

    @Override
    void deleteByGuid(String guid);

    Set<IDrone> findByRegionsIn(Set<RegionJpa> regions);
}
