package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.RoleJpa
import com.iscweb.persistence.repositories.impl.RoleJpaRepository
import spock.lang.Requires

import static groovy.test.GroovyAssert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class RoleRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<RoleJpa, RoleJpaRepository> {

    @Override
    protected RoleJpa createEntity() {
        return JpaGenerator.randomRole()
    }

    @Override
    protected void persistEntityReferences(RoleJpa jpa) {
//        No associated entities to persist.
    }

    @Override
    protected void deleteEntityReferences(RoleJpa jpa) {
//        No associated entities to delete.
    }

    @Override
    protected void compareProperties(RoleJpa initialJpa, RoleJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
        assertEquals(foundJpa.name, initialJpa.name)
    }

}