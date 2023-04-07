package com.iscweb.persistence

import com.iscweb.app.ControlCenter
import com.iscweb.common.model.IEntity
import com.iscweb.common.model.entity.IRegion
import com.iscweb.persistence.repositories.IPersistenceRepository
import com.iscweb.persistence.repositories.impl.RegionJpaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.ZonedDateTime

@SpringBootTest(classes = ControlCenter)
abstract class BaseRepositoryIntegrationTest<E extends IEntity, R extends IPersistenceRepository<E>> extends Specification {

    protected E originalJpa
    protected E updatedJpa

    @Autowired
    protected R repository

    @Autowired
    protected RegionJpaRepository regionRepository

    /**
     * Checks for the system environment variable "integration" in order to enable integration tests.
     * @return
     */
    protected static boolean integrationEnvironmentVariablePresent() {
        return System.getenv("integration") != null
    }

    def setup() {
        originalJpa = createEntity()
        persistEntityReferences(originalJpa)
        originalJpa = repository.save(originalJpa)
    }

    def cleanup() {
        if (repository.existsById(originalJpa.id)) {
            repository.deleteById(originalJpa.id)
        }
        deleteEntityReferences(originalJpa)
        if (updatedJpa != null) {
            if (repository.existsById(updatedJpa.id)) {
                repository.deleteById(updatedJpa.id)
            }
            deleteEntityReferences(updatedJpa)
        }
    }

    def "find entity"() {
        expect:
            E foundJpa = repository.findById(originalJpa.getId()).get()
            compareProperties(originalJpa, foundJpa)
    }

    def "update entity"() {
        given:
            initUpdatedEntity()

        when:
            repository.save(updatedJpa)
            E foundJpa = repository.findById(originalJpa.getId()).get()

        then:
            compareProperties(updatedJpa, foundJpa)
    }

    def "delete entity"() {
        when:
            repository.deleteById(originalJpa.getId())

        then:
            !repository.existsById(originalJpa.getId())
    }

    protected abstract E createEntity();

    protected abstract void compareProperties(E initialJpa, E foundJpa);

    protected void initUpdatedEntity() {
        updatedJpa = createEntity()

        updatedJpa.setId(originalJpa.getId())
        updatedJpa.setCreated(originalJpa.getCreated())
        updatedJpa.setUpdated(ZonedDateTime.now())

        persistEntityReferences(updatedJpa)
    }

    protected void persistEntityReferences(E jpa) {
        regionRepository.saveAll(jpa.regions)
    }


    protected void deleteEntityReferences(E jpa) {
        for (IRegion region  : jpa.regions) {
            regionRepository.deleteById(region.id)
        }
    }
}
