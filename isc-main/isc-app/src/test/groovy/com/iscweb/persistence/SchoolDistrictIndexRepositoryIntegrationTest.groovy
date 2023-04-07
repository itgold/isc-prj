package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.SchoolDistrictIndexJpa
import com.iscweb.persistence.repositories.impl.IndexJpaRepository
import com.iscweb.persistence.repositories.impl.SchoolDistrictIndexJpaRepository
import com.iscweb.persistence.repositories.impl.SchoolDistrictJpaRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Requires

import static groovy.test.GroovyAssert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class SchoolDistrictIndexRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<SchoolDistrictIndexJpa, SchoolDistrictIndexJpaRepository> {

    @Autowired
    protected SchoolDistrictJpaRepository schoolDistrictRepository

    @Autowired
    protected IndexJpaRepository indexRepository

    @Override
    protected SchoolDistrictIndexJpa createEntity() {
        return JpaGenerator.randomSchoolDistrictIndex()
    }

    @Override
    protected void persistEntityReferences(SchoolDistrictIndexJpa jpa) {
        regionRepository.save(jpa.schoolDistrict.region)
        schoolDistrictRepository.save(jpa.schoolDistrict)
        indexRepository.save(jpa.index)
    }

    @Override
    protected void deleteEntityReferences(SchoolDistrictIndexJpa jpa) {
        schoolDistrictRepository.deleteById(jpa.schoolDistrict.id)
        regionRepository.deleteById(jpa.schoolDistrict.region.id)
        indexRepository.deleteById(jpa.index.id)
    }

    @Override
    protected void compareProperties(SchoolDistrictIndexJpa initialJpa, SchoolDistrictIndexJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
//        Unlike in a SchoolIndex or IntegrationIndex, these can be updated.
        assertEquals(foundJpa.schoolDistrict, initialJpa.schoolDistrict)
        assertEquals(foundJpa.index, initialJpa.index)
    }

}