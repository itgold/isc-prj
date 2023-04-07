package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.entity.ICamera;
import com.iscweb.persistence.model.jpa.CameraJpa;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Repository for {@link CameraJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface CameraJpaRepository extends IPersistenceRepository<CameraJpa>, CameraJpaRepositoryCustom {

    CameraJpa findByGuid(String guid);

    CameraJpa findByExternalId(String externalId);

    @Override
    void deleteByGuid(String guid);

    Set<ICamera> findByRegionsIn(Set<RegionJpa> regions);
}
