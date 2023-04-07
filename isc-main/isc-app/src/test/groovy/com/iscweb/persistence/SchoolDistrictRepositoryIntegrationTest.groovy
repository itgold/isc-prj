package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.SchoolDistrictJpa
import com.iscweb.persistence.repositories.impl.SchoolDistrictJpaRepository
import spock.lang.Requires

import static groovy.test.GroovyAssert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class SchoolDistrictRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<SchoolDistrictJpa, SchoolDistrictJpaRepository> {

    @Override
    protected SchoolDistrictJpa createEntity() {
        return JpaGenerator.randomSchoolDistrict()
    }

    @Override
    protected void persistEntityReferences(SchoolDistrictJpa jpa) {
        regionRepository.save(jpa.region)
    }

    @Override
    protected void deleteEntityReferences(SchoolDistrictJpa jpa) {
        regionRepository.deleteById(jpa.region.id)
    }

    @Override
    protected void compareProperties(SchoolDistrictJpa initialJpa, SchoolDistrictJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
        assertEquals(foundJpa.name, initialJpa.name)
        assertEquals(foundJpa.contactEmail, initialJpa.contactEmail)
        assertEquals(foundJpa.address, initialJpa.address)
        assertEquals(foundJpa.city, initialJpa.city)
        assertEquals(foundJpa.state, initialJpa.state)
        assertEquals(foundJpa.zipCode, initialJpa.zipCode)
        assertEquals(foundJpa.country, initialJpa.country)
        assertEquals(foundJpa.status, initialJpa.status)

        assertEquals(foundJpa.region, initialJpa.region)
    }

}