package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.model.jpa.UtilityJpa;
import com.iscweb.persistence.model.jpa.UtilityTagJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Repository for {@link UtilityTagJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface UtilityTagJpaRepository extends IPersistenceRepository<UtilityTagJpa> {

    List<UtilityTagJpa> findAllByEntityGuid(String utilityGuid);

    void deleteByEntity(UtilityJpa utility);

    List<UtilityTagJpa> findAllByTagIn(List<TagJpa> tags);
}
