package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.DoorTagJpa
import com.iscweb.persistence.repositories.impl.DoorJpaRepository
import com.iscweb.persistence.repositories.impl.DoorTagJpaRepository
import com.iscweb.persistence.repositories.impl.TagJpaRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Requires

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class DoorTagRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<DoorTagJpa, DoorTagJpaRepository> {

    @Autowired
    protected DoorJpaRepository doorRepository

    @Autowired
    protected TagJpaRepository tagRepository

    @Override
    protected DoorTagJpa createEntity() {
        return JpaGenerator.randomDoorTag()
    }

    @Override
    protected void persistEntityReferences(DoorTagJpa jpa) {
        super.persistEntityReferences(jpa.entity)
        doorRepository.save(jpa.entity)
        tagRepository.save(jpa.tag)
    }

    @Override
    protected void deleteEntityReferences(DoorTagJpa jpa) {
        tagRepository.deleteById(jpa.tag.id)
        doorRepository.deleteById(jpa.entity.id)
        super.deleteEntityReferences(jpa.entity)
    }

    @Override
    protected void compareProperties(DoorTagJpa initialJpa, DoorTagJpa foundJpa) {
        foundJpa.getGuid() == initialJpa.getGuid()
        foundJpa.getEntity() == initialJpa.getEntity()
        foundJpa.getTag() == initialJpa.getTag()
//        foundJpa.getType() == initialJpa.getType()
    }

}
