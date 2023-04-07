package com.iscweb.persistence

import com.iscweb.persistence.model.jpa.RoleJpa
import com.iscweb.persistence.model.jpa.UserJpa
import com.iscweb.persistence.repositories.impl.RoleJpaRepository
import com.iscweb.persistence.repositories.impl.SchoolDistrictJpaRepository
import com.iscweb.persistence.repositories.impl.UserJpaRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Requires

import static groovy.test.GroovyAssert.assertEquals

@SuppressWarnings('UnnecessaryQualifiedReference')
@Requires({ BaseRepositoryIntegrationTest.integrationEnvironmentVariablePresent() })
class UserRepositoryIntegrationTest extends BaseRepositoryIntegrationTest<UserJpa, UserJpaRepository> {

    @Autowired
    protected SchoolDistrictJpaRepository schoolDistrictRepository

    @Autowired
    protected RoleJpaRepository roleRepository

    @Override
    protected UserJpa createEntity() {
        return JpaGenerator.randomUser()
    }

    @Override
    protected void persistEntityReferences(UserJpa jpa) {
        regionRepository.save(jpa.schoolDistrict.region)
        schoolDistrictRepository.save(jpa.schoolDistrict)
        for (RoleJpa role: jpa.roles) {
            roleRepository.save(role)
        }
    }

    @Override
    protected void deleteEntityReferences(UserJpa jpa) {
        schoolDistrictRepository.deleteById(jpa.schoolDistrict.id)
        regionRepository.deleteById(jpa.schoolDistrict.region.id)
        for (RoleJpa role: jpa.roles) {
            roleRepository.deleteById(role.id)
        }
    }

    @Override
    protected void compareProperties(UserJpa initialJpa, UserJpa foundJpa) {
        assertEquals(foundJpa.guid, initialJpa.guid)
        assertEquals(foundJpa.email, initialJpa.email)
        assertEquals(foundJpa.password, initialJpa.password)
        assertEquals(foundJpa.firstName, initialJpa.firstName)
        assertEquals(foundJpa.lastName, initialJpa.lastName)

        assertEquals(foundJpa.status, initialJpa.status)
        assertEquals(foundJpa.statusDate, initialJpa.statusDate)
        assertEquals(foundJpa.activationDate, initialJpa.activationDate)
        assertEquals(foundJpa.lastLogin, initialJpa.lastLogin)

        assertEquals(foundJpa.schoolDistrict, initialJpa.schoolDistrict)
        assertEquals(foundJpa.roles, initialJpa.roles)
    }

}