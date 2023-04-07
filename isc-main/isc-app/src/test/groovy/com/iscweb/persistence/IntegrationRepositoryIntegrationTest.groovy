package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.IntegrationJpa
import com.iscweb.persistence.repositories.impl.IntegrationJpaRepository
import spock.lang.Requires

import static groovy.test.GroovyAssert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class IntegrationRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<IntegrationJpa, IntegrationJpaRepository> {

    @Override
    protected IntegrationJpa createEntity() {
        return JpaGenerator.randomIntegration()
    }

    @Override
    protected void persistEntityReferences(IntegrationJpa jpa) {
//        No associated entities to persist.
    }

    @Override
    protected void deleteEntityReferences(IntegrationJpa jpa) {
//        No associated entities to delete.
    }

    @Override
    protected void compareProperties(IntegrationJpa initialJpa, IntegrationJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
        assertEquals(foundJpa.name, initialJpa.name)
        assertEquals(foundJpa.description, initialJpa.description)
        assertEquals(foundJpa.connectionParams, initialJpa.connectionParams)
        assertEquals(foundJpa.metaParams, initialJpa.metaParams)
        assertEquals(foundJpa.status, initialJpa.status)
    }

}