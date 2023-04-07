package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.CameraTagJpa
import com.iscweb.persistence.repositories.impl.CameraJpaRepository
import com.iscweb.persistence.repositories.impl.CameraTagJpaRepository
import com.iscweb.persistence.repositories.impl.TagJpaRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Requires

import static groovy.test.GroovyAssert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class CameraTagRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<CameraTagJpa, CameraTagJpaRepository> {

    @Autowired
    protected CameraJpaRepository cameraRepository

    @Autowired
    protected TagJpaRepository tagRepository

    @Override
    protected CameraTagJpa createEntity() {
        return JpaGenerator.randomCameraTag()
    }

    @Override
    protected void persistEntityReferences(CameraTagJpa jpa) {
        super.persistEntityReferences(jpa.entity)
        tagRepository.save(jpa.tag)
        cameraRepository.save(jpa.entity)
    }

    @Override
    protected void deleteEntityReferences(CameraTagJpa jpa) {
        tagRepository.deleteById(jpa.tag.id)
        cameraRepository.deleteById(jpa.entity.id)
        super.deleteEntityReferences(jpa.entity)
    }

    @Override
    protected void compareProperties(CameraTagJpa initialJpa, CameraTagJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
        assertEquals(foundJpa.entity, initialJpa.entity)
        assertEquals(foundJpa.tag, initialJpa.tag)
//        assertEquals(foundJpa.type, initialJpa.type)
    }

}
