package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for {@link TagJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface TagJpaRepository extends IPersistenceRepository<TagJpa>, TagJpaRepositoryCustom {

    TagJpa findByGuid(String guid);

    List<TagJpa> findAllByNameIn(List<String> tags);
}
