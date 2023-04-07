package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.DoorJpa;
import com.iscweb.persistence.model.jpa.DoorTagJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for {@link DoorTagJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface DoorTagJpaRepository extends IPersistenceRepository<DoorTagJpa> {

    List<DoorTagJpa> findAllByEntityGuid(String doorGuid);

    void deleteByEntity(DoorJpa door);

    List<DoorTagJpa> findAllByTagIn(List<TagJpa> tags);
}
