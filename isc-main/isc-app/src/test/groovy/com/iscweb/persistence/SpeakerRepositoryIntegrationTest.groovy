package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.SpeakerJpa
import com.iscweb.persistence.repositories.impl.SpeakerJpaRepository
import spock.lang.Requires

import static groovy.test.GroovyAssert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class SpeakerRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<SpeakerJpa, SpeakerJpaRepository> {

    @Override
    protected SpeakerJpa createEntity() {
        return JpaGenerator.randomSpeaker()
    }

    @Override
    protected void compareProperties(SpeakerJpa initialJpa, SpeakerJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
        assertEquals(foundJpa.externalId, initialJpa.externalId)
        assertEquals(foundJpa.status, initialJpa.status)
        assertEquals(foundJpa.type, initialJpa.type)

        assertEquals(foundJpa.regions, initialJpa.regions)
    }

}
