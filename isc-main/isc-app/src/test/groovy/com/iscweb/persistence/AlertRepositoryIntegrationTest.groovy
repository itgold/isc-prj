package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.AlertJpa
import com.iscweb.persistence.repositories.impl.AlertJpaRepository
import spock.lang.Requires

import static groovy.test.GroovyAssert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class AlertRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<AlertJpa, AlertJpaRepository> {

    @Override
    protected AlertJpa createEntity() {
        return JpaGenerator.randomAlert()
    }

    @Override
    protected void persistEntityReferences(AlertJpa jpa) {
//        No associated entities to persist.
    }

    @Override
    protected void deleteEntityReferences(AlertJpa jpa) {
//        No associated entities to delete.
    }

    protected void compareProperties(AlertJpa initialJpa, AlertJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
        assertEquals(foundJpa.name, initialJpa.name)
        assertEquals(foundJpa.severity, initialJpa.severity)
    }

}
