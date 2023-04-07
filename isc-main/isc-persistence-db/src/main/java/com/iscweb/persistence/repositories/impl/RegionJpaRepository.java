package com.iscweb.persistence.repositories.impl;

import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.model.jpa.SchoolDistrictJpa;
import com.iscweb.persistence.repositories.IPersistenceRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Repository for {@link SchoolDistrictJpa} objects.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED, transactionManager = "jpaTransactionManager")
public interface RegionJpaRepository extends IPersistenceRepository<RegionJpa>, RegionJpaRepositoryCustom {

    RegionJpa findByGuid(String guid);

    Set<RegionJpa> findByGuidIn(Set<String> guid);

    @Override
    void deleteByGuid(String guid);

    Set<RegionJpa> findByRegionsIn(Set<RegionJpa> regionJpa);

    Set<RegionJpa> findByNameStartingWith(String regionName);

    @Query("SELECT r FROM RegionJpa r WHERE r.name like ?1% AND ?2 member of r.regions")
    Set<RegionJpa> findByNameStartingWithAndMemberOfRegions(String regionName, RegionJpa parent);
}
