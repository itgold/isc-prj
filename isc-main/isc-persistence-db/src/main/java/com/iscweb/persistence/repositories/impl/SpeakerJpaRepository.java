package com.iscweb.persistence.repositories.impl;

import com.iscweb.common.model.entity.ISpeaker;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.model.jpa.SpeakerJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Repository for {@link SpeakerJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface SpeakerJpaRepository extends IPersistenceRepository<SpeakerJpa>, SpeakerJpaRepositoryCustom {

    SpeakerJpa findByGuid(String guid);

    SpeakerJpa findByExternalId(String externalId);

    Set<ISpeaker> findByRegionsIn(Set<RegionJpa> regions);
}
