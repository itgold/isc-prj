package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.SchoolIndexJpa
import com.iscweb.persistence.repositories.impl.IndexJpaRepository
import com.iscweb.persistence.repositories.impl.SchoolDistrictJpaRepository
import com.iscweb.persistence.repositories.impl.SchoolIndexJpaRepository
import com.iscweb.persistence.repositories.impl.SchoolJpaRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Requires

import static groovy.test.GroovyAssert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class SchoolIndexRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<SchoolIndexJpa, SchoolIndexJpaRepository> {

    @Autowired
    protected SchoolDistrictJpaRepository schoolDistrictRepository

    @Autowired
    protected SchoolJpaRepository schoolRepository

    @Autowired
    protected IndexJpaRepository indexRepository

    @Override
    protected SchoolIndexJpa createEntity() {
        return JpaGenerator.randomSchoolIndex()
    }

    @Override
    protected void persistEntityReferences(SchoolIndexJpa jpa) {
        regionRepository.save(jpa.school.schoolDistrict.region)
        schoolDistrictRepository.save(jpa.school.schoolDistrict)
        regionRepository.save(jpa.school.region)
        schoolRepository.save(jpa.school)
        indexRepository.save(jpa.index)
    }

    @Override
    protected void deleteEntityReferences(SchoolIndexJpa jpa) {
        indexRepository.deleteById(jpa.index.id)
        schoolRepository.deleteById(jpa.school.id)
        regionRepository.deleteById(jpa.school.region.id)
        schoolDistrictRepository.deleteById(jpa.school.schoolDistrict.id)
        regionRepository.deleteById(jpa.school.schoolDistrict.region.id)
    }

    @Override
    protected void compareProperties(SchoolIndexJpa initialJpa, SchoolIndexJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
//        These cannot be updated...
//        assertEquals(foundJpa.school, initialJpa.school)
//        assertEquals(foundJpa.index, initialJpa.index)
    }

}
