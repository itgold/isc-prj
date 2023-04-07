package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.SchoolJpa
import com.iscweb.persistence.repositories.impl.SchoolDistrictJpaRepository
import com.iscweb.persistence.repositories.impl.SchoolJpaRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Requires

import static groovy.test.GroovyAssert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class SchoolRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<SchoolJpa, SchoolJpaRepository> {

    @Autowired
    protected SchoolDistrictJpaRepository schoolDistrictRepository

    @Override
    protected SchoolJpa createEntity() {
        return JpaGenerator.randomSchool()
    }

    @Override
    protected void persistEntityReferences(SchoolJpa jpa) {
        regionRepository.save(jpa.schoolDistrict.region)
        schoolDistrictRepository.save(jpa.schoolDistrict)
        regionRepository.save(jpa.region)
    }

    @Override
    protected void deleteEntityReferences(SchoolJpa jpa) {
        schoolDistrictRepository.deleteById(jpa.schoolDistrict.id)
        regionRepository.deleteById(jpa.schoolDistrict.region.id)
        regionRepository.deleteById(jpa.region.id)
    }

    @Override
    protected void compareProperties(SchoolJpa initialJpa, SchoolJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
        assertEquals(foundJpa.name, initialJpa.name)
        assertEquals(foundJpa.contactEmail, initialJpa.contactEmail)

        assertEquals(foundJpa.address, initialJpa.address)
        assertEquals(foundJpa.city, initialJpa.city)
        assertEquals(foundJpa.state, initialJpa.state)
        assertEquals(foundJpa.zipCode, initialJpa.zipCode)
        assertEquals(foundJpa.country, initialJpa.country)

        assertEquals(foundJpa.status, initialJpa.status)
        assertEquals(foundJpa.schoolDistrict, initialJpa.schoolDistrict)

        assertEquals(foundJpa.region, initialJpa.region)
    }

}