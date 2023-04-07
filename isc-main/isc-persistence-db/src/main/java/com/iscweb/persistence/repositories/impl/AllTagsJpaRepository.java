package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.BaseEntityTagJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for {@link BaseEntityTagJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface AllTagsJpaRepository extends IPersistenceRepository<BaseEntityTagJpa> {
    List<BaseEntityTagJpa> findByTag(TagJpa tag);

    @Modifying
    @Query(value = "DELETE FROM BaseEntityTagJpa tej WHERE tej.tag.id = ?1")
    void deleteByTagId(Long tagId);

}
