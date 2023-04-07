package com.iscweb.service.converter

import com.iscweb.common.model.dto.entity.core.DroneDto
import com.iscweb.common.model.metadata.ConverterType
import com.iscweb.common.model.metadata.DroneStatus
import com.iscweb.common.model.metadata.DroneType
import com.iscweb.common.util.TrackableDtoProxy
import com.iscweb.persistence.model.jpa.DeviceStateItemJpa
import com.iscweb.persistence.model.jpa.DroneJpa
import com.iscweb.service.converter.impl.DroneConverter
import spock.lang.Specification

class DroneConverterTest extends Specification {

    def "updateSameJpaInstance"() {
        given:
            DroneConverter droneConverter = new DroneConverter(new Convert.ConvertContext())
            DroneJpa jpa = new DroneJpa()
            droneConverter.withJpa(jpa)
            DroneDto dto = TrackableDtoProxy.getProxy(new DroneDto())

            String externalId = "20cf5093-4245-468d-b9f1-069fcf7932a1"
            dto.setExternalId(externalId)
            dto.setType(DroneType.TETHERED)
            dto.setName("Drone_name")
            dto.setDescription("Drone_description")

            droneConverter.setSource(dto)

            DroneJpa resultJpa

        when:
            resultJpa = droneConverter.toJpa()

        then:
            resultJpa == jpa

        when:
            resultJpa = droneConverter.toJpa()

        then:
            resultJpa.getExternalId() == dto.getExternalId()
            resultJpa.getName() == dto.getName()
            resultJpa.getDescription() == dto.getDescription()
            resultJpa.getType() == dto.getType()
            resultJpa.getStatus() == DroneStatus.ACTIVATED
            resultJpa.getConverterType() == ConverterType.DRONE

        when:
            dto.setExternalId(null)
            resultJpa = droneConverter.toJpa()

        then:
            resultJpa.getExternalId() == externalId
            resultJpa == jpa

        when:
            dto.setStatus(DroneStatus.DEACTIVATED)
            resultJpa = droneConverter.toJpa()

        then:
            resultJpa.getStatus() == dto.getStatus()

        when:
            dto.setName("new_drone_name")
            dto.setDescription("new_drone_description")
            //not sure how to check when drone type is modified: there is only one drone type defined
            dto.setType(DroneType.TETHERED)
            resultJpa = droneConverter.toJpa()

        then:
            resultJpa.getName() == dto.getName()
            resultJpa.getDescription() == dto.getDescription()
            resultJpa.getType() == dto.getType()

        when:
            dto.setStatus(null)
            resultJpa = droneConverter.toJpa()

        then:
            resultJpa.getStatus() == DroneStatus.ACTIVATED
    }

    def "generateNewJpaInstance"() {
        given:
            DroneConverter droneConverter = new DroneConverter(new Convert.ConvertContext())
            DroneJpa jpa
            DroneJpa anotherCallJpa
            DroneDto dto = new DroneDto()
            dto.setExternalId("20cf5093-4245-468d-b9f1-069fcf7932a1")
            dto.setType(DroneType.TETHERED)
            dto.setName("Drone_name")
            dto.setDescription("Drone_zone5")

            droneConverter.setSource(dto)

        when:
            jpa = droneConverter.toJpa()

        then:
            jpa.getExternalId() == dto.getExternalId()
            jpa.getName() == dto.getName()
            jpa.getDescription() == dto.getDescription()
            jpa.getType() == dto.getType()
            jpa.getStatus() == DroneStatus.ACTIVATED
            jpa.getConverterType() == ConverterType.DRONE

        when:
            anotherCallJpa = droneConverter.toJpa()

        then:
            anotherCallJpa != jpa
            anotherCallJpa.getExternalId() == dto.getExternalId()
            anotherCallJpa.getName() == dto.getName()
            anotherCallJpa.getDescription() == dto.getDescription()
            anotherCallJpa.getType() == dto.getType()
            anotherCallJpa.getStatus() == DroneStatus.ACTIVATED
            anotherCallJpa.getConverterType() == ConverterType.DRONE

        when:
            dto.setStatus(DroneStatus.DEACTIVATED)
            anotherCallJpa = droneConverter.toJpa()

        then:
            anotherCallJpa.getStatus() == dto.getStatus()
    }

    def "toDto"() {
        given:
            DroneConverter droneConverter = new DroneConverter(new Convert.ConvertContext())
            DroneDto dto
            DroneJpa jpa = new DroneJpa()
            jpa.setExternalId("20cf5093-4245-468d-b9f1-069fcf7932a1")
            jpa.setType(DroneType.TETHERED)
            jpa.setDescription("description")
            jpa.setName("name")

            DeviceStateItemJpa stateItemJpa = new DeviceStateItemJpa()
            stateItemJpa.setName("state")
            Set<DeviceStateItemJpa> statesSet = new HashSet()
            statesSet.add(stateItemJpa)
            jpa.setState(statesSet)

            droneConverter.setSource(jpa)
            droneConverter.scope(Scope.METADATA)

        when:
            dto = droneConverter.toDto()

        then:
            dto.getExternalId() == jpa.getExternalId()
            dto.getType() == jpa.getType()
            dto.getStatus() == DroneStatus.ACTIVATED
            dto.getDescription() == jpa.getDescription()
            dto.getName() == jpa.getName()

            dto.getConverterType() == ConverterType.DRONE
            dto.getState().size() == 1
            dto.getState()[0].getType() == "state"

        when:
            jpa.setStatus(DroneStatus.DEACTIVATED)
            dto = droneConverter.toDto()

        then:
            dto.getStatus() == jpa.getStatus()
    }
}
