package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.CameraJpa
import com.iscweb.persistence.repositories.impl.CameraJpaRepository
import spock.lang.Requires

import static org.junit.Assert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class CameraRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<CameraJpa, CameraJpaRepository> {

    @Override
    protected CameraJpa createEntity() {
        return JpaGenerator.randomCamera()
    }

    @Override
    protected void compareProperties(CameraJpa initialJpa, CameraJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
        assertEquals(foundJpa.externalId, initialJpa.externalId)
        assertEquals(foundJpa.status, initialJpa.status)
        assertEquals(foundJpa.type, initialJpa.type)

        assertEquals(foundJpa.regions, initialJpa.regions)
    }

}
