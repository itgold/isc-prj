package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.SpeakerTagJpa
import com.iscweb.persistence.repositories.impl.SpeakerJpaRepository
import com.iscweb.persistence.repositories.impl.SpeakerTagJpaRepository
import com.iscweb.persistence.repositories.impl.TagJpaRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Requires

import static groovy.test.GroovyAssert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class SpeakerTagRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<SpeakerTagJpa, SpeakerTagJpaRepository> {

    @Autowired
    protected SpeakerJpaRepository cameraRepository

    @Autowired
    protected TagJpaRepository tagRepository

    @Override
    protected SpeakerTagJpa createEntity() {
        return JpaGenerator.randomSpeakerTag()
    }

    @Override
    protected void persistEntityReferences(SpeakerTagJpa jpa) {
        super.persistEntityReferences(jpa.entity)
        cameraRepository.save(jpa.entity)
        tagRepository.save(jpa.tag)
    }

    @Override
    protected void deleteEntityReferences(SpeakerTagJpa jpa) {
        tagRepository.deleteById(jpa.tag.id)
        cameraRepository.deleteById(jpa.entity.id)
        super.deleteEntityReferences(jpa.entity)
    }

    @Override
    protected void compareProperties(SpeakerTagJpa initialJpa, SpeakerTagJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
        assertEquals(foundJpa.entity, initialJpa.entity)
        assertEquals(foundJpa.tag, initialJpa.tag)
//        assertEquals(foundJpa.type, initialJpa.type)
    }

}
