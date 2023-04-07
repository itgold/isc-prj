package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.IndexJpa
import com.iscweb.persistence.repositories.impl.IndexJpaRepository
import spock.lang.Requires

import static groovy.test.GroovyAssert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class IndexRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<IndexJpa, IndexJpaRepository> {

    @Override
    protected IndexJpa createEntity() {
        return JpaGenerator.randomIndex()
    }

    @Override
    protected void persistEntityReferences(IndexJpa jpa) {
//        No associated entities to persist.
    }

    @Override
    protected void deleteEntityReferences(IndexJpa jpa) {
//        No associated entities to delete.
    }

    @Override
    protected void compareProperties(IndexJpa initialJpa, IndexJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
        assertEquals(foundJpa.name, initialJpa.name)
        assertEquals(foundJpa.description, initialJpa.description)
        assertEquals(foundJpa.status, initialJpa.status)
    }

}