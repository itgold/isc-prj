package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.model.jpa.SafetyJpa;
import com.iscweb.persistence.model.jpa.SafetyTagJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for {@link SafetyTagJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface SafetyTagJpaRepository extends IPersistenceRepository<SafetyTagJpa> {

    List<SafetyTagJpa> findAllByEntityGuid(String safetyGuid);

    void deleteByEntity(SafetyJpa safety);

    List<SafetyTagJpa> findAllByTagIn(List<TagJpa> tags);
}
