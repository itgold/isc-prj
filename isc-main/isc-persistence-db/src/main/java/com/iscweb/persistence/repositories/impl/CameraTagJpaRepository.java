package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.CameraJpa;
import com.iscweb.persistence.model.jpa.CameraTagJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for {@link CameraTagJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface CameraTagJpaRepository extends IPersistenceRepository<CameraTagJpa> {

    List<CameraTagJpa> findAllByEntityGuid(String guid);

    void deleteByEntity(CameraJpa camera);

    List<CameraTagJpa> findAllByTagIn(List<TagJpa> tags);
}
