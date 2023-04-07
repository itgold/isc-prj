package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.SpeakerJpa;
import com.iscweb.persistence.model.jpa.SpeakerTagJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for {@link SpeakerTagJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface SpeakerTagJpaRepository extends IPersistenceRepository<SpeakerTagJpa> {

    List<SpeakerTagJpa> findAllByEntityGuid(String guid);

    void deleteByEntity(SpeakerJpa speakerJpa);

    List<SpeakerTagJpa> findAllByTagIn(List<TagJpa> tags);
}
