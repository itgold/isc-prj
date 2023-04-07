package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.RadioJpa;
import com.iscweb.persistence.model.jpa.RadioTagJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for {@link RadioTagJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface RadioTagJpaRepository extends IPersistenceRepository<RadioTagJpa> {

    List<RadioTagJpa> findAllByEntityGuid(String radioGuid);

    void deleteByEntity(RadioJpa radio);

    List<RadioTagJpa> findAllByTagIn(List<TagJpa> tags);
}
