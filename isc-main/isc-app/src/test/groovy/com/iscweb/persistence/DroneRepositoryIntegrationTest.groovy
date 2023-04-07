package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.DroneJpa
import com.iscweb.persistence.repositories.impl.DroneJpaRepository
import spock.lang.Requires

import static groovy.test.GroovyAssert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class DroneRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<DroneJpa, DroneJpaRepository> {

    @Override
    protected DroneJpa createEntity() {
        return JpaGenerator.randomDrone()
    }

    @Override
    protected void compareProperties(DroneJpa initialJpa, DroneJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
        assertEquals(foundJpa.externalId, initialJpa.externalId)
        assertEquals(foundJpa.type, initialJpa.type)
        assertEquals(foundJpa.status, initialJpa.status)

        assertEquals(foundJpa.geoLocation, initialJpa.geoLocation)
        assertEquals(foundJpa.regions, initialJpa.regions)
    }

}
