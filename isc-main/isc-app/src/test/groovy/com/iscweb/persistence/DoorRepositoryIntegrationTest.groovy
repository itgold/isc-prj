package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.DoorJpa
import com.iscweb.persistence.repositories.impl.DoorJpaRepository
import spock.lang.Requires

import static groovy.test.GroovyAssert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class DoorRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<DoorJpa, DoorJpaRepository> {

    @Override
    protected DoorJpa createEntity() {
        return JpaGenerator.randomDoor()
    }

    @Override
    protected void compareProperties(DoorJpa initialJpa, DoorJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
        assertEquals(foundJpa.externalId, initialJpa.externalId)
        assertEquals(foundJpa.name, initialJpa.name)
        assertEquals(foundJpa.description, initialJpa.description)
        assertEquals(foundJpa.batteryLevel, initialJpa.batteryLevel)

        assertEquals(foundJpa.status, initialJpa.status)
        assertEquals(foundJpa.type, initialJpa.type)
        assertEquals(foundJpa.connectionStatus, initialJpa.connectionStatus)
        assertEquals(foundJpa.onlineStatus, initialJpa.onlineStatus)
        assertEquals(foundJpa.batteryLevel, initialJpa.batteryLevel)
        assertEquals(foundJpa.tamperStatus, initialJpa.tamperStatus)
        assertEquals(foundJpa.openingMode, initialJpa.openingMode)
    }
}
