package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.DroneJpa;
import com.iscweb.persistence.model.jpa.DroneTagJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for {@link DroneTagJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface DroneTagJpaRepository extends IPersistenceRepository<DroneTagJpa> {

    List<DroneTagJpa> findAllByEntityGuid(String guid);

    void deleteByEntity(DroneJpa entity);

    List<DroneTagJpa> findAllByTagIn(List<TagJpa> tags);
}
