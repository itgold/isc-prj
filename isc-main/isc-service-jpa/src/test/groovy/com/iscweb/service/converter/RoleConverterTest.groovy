package com.iscweb.service.converter

import com.iscweb.common.model.dto.entity.core.RoleDto
import com.iscweb.common.util.TrackableDtoProxy
import com.iscweb.persistence.model.jpa.RoleJpa
import com.iscweb.service.converter.impl.RoleConverter
import spock.lang.Specification

class RoleConverterTest extends Specification {
    def "updateSameJpaInstance"() {

        given:
            RoleConverter roleConverter = new RoleConverter(new Convert.ConvertContext())
            RoleJpa jpa = new RoleJpa()
            roleConverter.withJpa(jpa)
            RoleDto dto = TrackableDtoProxy.getProxy(new RoleDto())
            String RoleName = "Role_name"
            dto.setName(RoleName)

            roleConverter.setSource(dto)

            RoleJpa resultJpa

        when:
            resultJpa = roleConverter.toJpa()

        then:
            resultJpa == jpa
            resultJpa.getName() == dto.getName()

        when:
            dto.setName(null)
            resultJpa = roleConverter.toJpa()

        then:
            resultJpa.getName() == null
    }

    def "generateNewJpaInstance"() {
        given:
            RoleConverter roleConverter = new RoleConverter(new Convert.ConvertContext())
            RoleDto dto = new RoleDto()
            String roleName = "Role_name"
            dto.setName(roleName)

            roleConverter.setSource(dto)

            RoleJpa resultJpa
            RoleJpa anotherResultJpa

        when:
            dto.setName(null)
            resultJpa = roleConverter.toJpa()

        then:
            resultJpa.getName() == null

        when:
            anotherResultJpa = roleConverter.toJpa()

        then:
            resultJpa != anotherResultJpa

        when:
            dto.setName(roleName)
            resultJpa = roleConverter.toJpa()

        then:
            resultJpa.getName() == roleName
    }

    def "toDto"() {
        given:
            RoleConverter roleConverter = new RoleConverter(new Convert.ConvertContext())
            RoleJpa jpa = new RoleJpa()
            RoleDto resultDto
            RoleDto anotherResultDto

        when:
            resultDto = roleConverter.toDto()

        then:
            resultDto == null

        when:
            roleConverter.setSource(jpa)
            resultDto = roleConverter.toDto()

        then:
            resultDto

        when:
            String roleName = "Role_name"
            jpa.setName(roleName)
            resultDto = roleConverter.toDto()

        then:
            resultDto.getName() == roleName


        when:
            anotherResultDto = roleConverter.toDto()

        then:
            resultDto.getName() == anotherResultDto.getName()
            resultDto != anotherResultDto

    }
}
