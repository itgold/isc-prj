package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.IntegrationIndexJpa
import com.iscweb.persistence.repositories.impl.IndexJpaRepository
import com.iscweb.persistence.repositories.impl.IntegrationIndexJpaRepository
import com.iscweb.persistence.repositories.impl.IntegrationJpaRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Requires

import static groovy.test.GroovyAssert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class IntegrationIndexRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<IntegrationIndexJpa, IntegrationIndexJpaRepository> {

    @Autowired
    protected IndexJpaRepository indexRepository

    @Autowired
    protected IntegrationJpaRepository integrationRepository

    @Override
    protected IntegrationIndexJpa createEntity() {
        return JpaGenerator.randomIntegrationIndex()
    }

    @Override
    protected void persistEntityReferences(IntegrationIndexJpa jpa) {
        indexRepository.save(jpa.index)
        integrationRepository.save(jpa.integration)
    }

    @Override
    protected void deleteEntityReferences(IntegrationIndexJpa jpa) {
        indexRepository.deleteById(jpa.index.id)
        integrationRepository.deleteById(jpa.integration.id)
    }

    @Override
    protected void compareProperties(IntegrationIndexJpa initialJpa, IntegrationIndexJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
//        These cannot be updated...
//        assertEquals(foundJpa.index, initialJpa.index)
//        assertEquals(foundJpa.integration, initialJpa.integration)
    }

}