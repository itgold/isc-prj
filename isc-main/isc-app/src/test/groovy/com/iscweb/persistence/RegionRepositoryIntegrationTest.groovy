package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.RegionJpa
import com.iscweb.persistence.repositories.impl.RegionJpaRepository
import com.iscweb.persistence.repositories.impl.SchoolDistrictJpaRepository
import com.iscweb.persistence.repositories.impl.SchoolJpaRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Requires

import static groovy.test.GroovyAssert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class RegionRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<RegionJpa, RegionJpaRepository>{

    @Autowired
    protected SchoolJpaRepository schoolRepository

    @Autowired
    protected SchoolDistrictJpaRepository schoolDistrictRepository

    @Override
    protected RegionJpa createEntity() {
        return JpaGenerator.randomRegion()
    }

    @Override
    protected void persistEntityReferences(RegionJpa jpa) {
    }

    @Override
    protected void deleteEntityReferences(RegionJpa jpa) {
    }

    @Override
    protected void compareProperties(RegionJpa initialJpa, RegionJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
        assertEquals(foundJpa.name, initialJpa.name)
        assertEquals(foundJpa.status, initialJpa.status)
    }

}