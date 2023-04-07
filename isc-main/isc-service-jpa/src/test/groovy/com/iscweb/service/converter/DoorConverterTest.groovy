package com.iscweb.service.converter

import com.iscweb.common.model.dto.entity.core.DoorDto
import com.iscweb.common.model.metadata.*
import com.iscweb.common.util.TrackableDtoProxy
import com.iscweb.persistence.model.jpa.DeviceStateItemJpa
import com.iscweb.persistence.model.jpa.DoorJpa
import com.iscweb.service.converter.impl.DoorConverter
import spock.lang.Specification

class DoorConverterTest extends Specification {

    def "updateSameJpaInstance"() {
        given:
            DoorConverter doorConverter = new DoorConverter(new Convert.ConvertContext())
            DoorJpa jpa = new DoorJpa()
            doorConverter.withJpa(jpa)
            DoorDto dto = TrackableDtoProxy.getProxy(new DoorDto())

            String externalId = "e62a0fa7-8aac-4633-9f0c-32b08918ca5a"
            dto.setExternalId(externalId)
            dto.setName("DOOR - 100")
            dto.setDescription("back garden door")
            dto.setType(DoorType.GATE)
            dto.setConnectionStatus(DoorConnectionStatus.ONLINE)
            dto.setOnlineStatus(DoorOnlineStatus.LEFT_OPENED)
            dto.setBatteryStatus(DoorBatteryStatus.LOW)
            dto.setTamperStatus(DoorTamperStatus.UNKNOWN)
            dto.setOpeningMode(DoorOpeningMode.KEYPAD_ONLY)
            dto.setBatteryLevel(55)
            dto.setUpdateRequired(false)

            doorConverter.setSource(dto)

            DoorJpa resultJpa

        when:
            resultJpa = doorConverter.toJpa()

        then:
            resultJpa == jpa

        when:
            resultJpa = doorConverter.toJpa()

        then:
            resultJpa.getExternalId() == dto.getExternalId()
            resultJpa.getName() == dto.getName()
            resultJpa.getDescription() == dto.getDescription()
            resultJpa.getType() == dto.getType()
            resultJpa.getStatus() == DoorStatus.ACTIVATED
            resultJpa.getConnectionStatus() == dto.getConnectionStatus()
            resultJpa.getOnlineStatus() == dto.getOnlineStatus()
            resultJpa.getBatteryStatus() == dto.getBatteryStatus()
            resultJpa.getTamperStatus() == dto.getTamperStatus()
            resultJpa.getOpeningMode() == dto.getOpeningMode()
            resultJpa.getBatteryLevel() == dto.getBatteryLevel()
            resultJpa.isUpdateRequired() == dto.getUpdateRequired()
            resultJpa.getConverterType() == ConverterType.DOOR

        when:
            dto.setExternalId(null)
            resultJpa = doorConverter.toJpa()

        then:
            resultJpa.getExternalId() == externalId
            resultJpa == jpa

        when:
            dto.setStatus(DoorStatus.DEACTIVATED)
            resultJpa = doorConverter.toJpa()

        then:
            resultJpa.getStatus() == dto.getStatus()

        when:
            dto.setName("modified_door_name")
            dto.setDescription("modified_door_description")
            dto.setType(DoorType.GATE)
            dto.setConnectionStatus(DoorConnectionStatus.ONLINE)
            dto.setOnlineStatus(DoorOnlineStatus.OPENED)
            dto.setBatteryStatus(DoorBatteryStatus.LOW)
            dto.setTamperStatus(DoorTamperStatus.UNKNOWN)
            dto.setOpeningMode(DoorOpeningMode.KEYPAD_ONLY)
            dto.setBatteryLevel(45)
            dto.setUpdateRequired(false)
            resultJpa = doorConverter.toJpa()

        then:
            resultJpa.getName() == dto.getName()
            resultJpa.getDescription() == dto.getDescription()
            resultJpa.getType() == dto.getType()
            resultJpa.getStatus() == DoorStatus.DEACTIVATED
            resultJpa.getConnectionStatus() == dto.getConnectionStatus()
            resultJpa.getOnlineStatus() == dto.getOnlineStatus()
            resultJpa.getBatteryStatus() == dto.getBatteryStatus()
            resultJpa.getTamperStatus() == dto.getTamperStatus()
            resultJpa.getOpeningMode() == dto.getOpeningMode()
            resultJpa.getBatteryLevel() == dto.getBatteryLevel()
            resultJpa.isUpdateRequired() == dto.getUpdateRequired()
            resultJpa.getConverterType() == ConverterType.DOOR

        when:
            dto.setStatus(null)
            resultJpa = doorConverter.toJpa()

        then:
            resultJpa.getStatus() == DoorStatus.ACTIVATED
    }

    def "generateNewJpaInstance"() {
        given:
            DoorConverter doorConverter = new DoorConverter(new Convert.ConvertContext())
            DoorJpa jpa
            DoorJpa anotherCallJpa
            DoorDto dto = new DoorDto()
            dto.setExternalId("e62a0fa7-8aac-4633-9f0c-32b08918ca5a")
            dto.setName("DOOR - 100")
            dto.setDescription("back garden door")
            dto.setType(DoorType.GATE)
            dto.setConnectionStatus(DoorConnectionStatus.ONLINE)
            dto.setOnlineStatus(DoorOnlineStatus.LEFT_OPENED)
            dto.setBatteryStatus(DoorBatteryStatus.LOW)
            dto.setTamperStatus(DoorTamperStatus.UNKNOWN)
            dto.setOpeningMode(DoorOpeningMode.KEYPAD_ONLY)
            dto.setBatteryLevel(55)
            dto.setUpdateRequired(false)

            doorConverter.setSource(dto)

        when:
            jpa = doorConverter.toJpa()

        then:
            jpa.getExternalId() == dto.getExternalId()
            jpa.getName() == dto.getName()
            jpa.getDescription() == dto.getDescription()
            jpa.getType() == dto.getType()
            jpa.getStatus() == DoorStatus.ACTIVATED
            jpa.getConnectionStatus() == dto.getConnectionStatus()
            jpa.getOnlineStatus() == dto.getOnlineStatus()
            jpa.getBatteryStatus() == dto.getBatteryStatus()
            jpa.getTamperStatus() == dto.getTamperStatus()
            jpa.getOpeningMode() == dto.getOpeningMode()
            jpa.getBatteryLevel() == dto.getBatteryLevel()
            jpa.isUpdateRequired() == dto.getUpdateRequired()
            jpa.getConverterType() == ConverterType.DOOR

        when:
            anotherCallJpa = doorConverter.toJpa()

        then:
            anotherCallJpa != jpa
            anotherCallJpa.getExternalId() == dto.getExternalId()
            anotherCallJpa.getName() == dto.getName()
            anotherCallJpa.getDescription() == dto.getDescription()
            anotherCallJpa.getType() == dto.getType()
            anotherCallJpa.getStatus() == DoorStatus.ACTIVATED
            anotherCallJpa.getConnectionStatus() == dto.getConnectionStatus()
            anotherCallJpa.getOnlineStatus() == dto.getOnlineStatus()
            anotherCallJpa.getBatteryStatus() == dto.getBatteryStatus()
            anotherCallJpa.getTamperStatus() == dto.getTamperStatus()
            anotherCallJpa.getOpeningMode() == dto.getOpeningMode()
            anotherCallJpa.getBatteryLevel() == dto.getBatteryLevel()
            anotherCallJpa.isUpdateRequired() == dto.getUpdateRequired()
            anotherCallJpa.getConverterType() == ConverterType.DOOR

        when:
            dto.setStatus(DoorStatus.DEACTIVATED)
            anotherCallJpa = doorConverter.toJpa()

        then:
            anotherCallJpa.getStatus() == dto.getStatus()
    }

    def "toDto"() {
        given:
            DoorConverter doorConverter = new DoorConverter(new Convert.ConvertContext())
            DoorDto dto
            DoorJpa jpa = new DoorJpa()
            jpa.setExternalId("20cf5093-4245-468d-b9f1-069fcf7932a1")
            jpa.setName("name")
            jpa.setDescription("description")
            jpa.setType(DoorType.PERIMETER)
            jpa.setConnectionStatus(DoorConnectionStatus.NO_COMMUNICATION)
            jpa.setOnlineStatus(DoorOnlineStatus.EMERGENCY_CLOSE)
            jpa.setBatteryStatus(DoorBatteryStatus.VERY_LOW)
            jpa.setTamperStatus(DoorTamperStatus.ALARMED)
            jpa.setOpeningMode(DoorOpeningMode.STANDARD)
            jpa.setBatteryLevel(1)
            jpa.setUpdateRequired(true)

            DeviceStateItemJpa stateItemJpa = new DeviceStateItemJpa()
            stateItemJpa.name = 'state'
            Set<DeviceStateItemJpa> statesSet = new HashSet()
            statesSet.add(stateItemJpa)
            jpa.setState(statesSet)

            doorConverter.setSource(jpa)
            doorConverter.scope(Scope.METADATA)

        when:
            dto = doorConverter.toDto()

        then:
            dto.getExternalId() == jpa.getExternalId()
            dto.getName() == jpa.getName()
            dto.getDescription() == jpa.getDescription()
            dto.getType() == jpa.getType()
            dto.getStatus() == DoorStatus.ACTIVATED
            dto.getConnectionStatus() == jpa.getConnectionStatus()
            dto.getOnlineStatus() == jpa.onlineStatus
            dto.getBatteryStatus() == jpa.getBatteryStatus()
            dto.getTamperStatus() == jpa.getTamperStatus()
            dto.getOpeningMode() == jpa.getOpeningMode()
            dto.getBatteryLevel() == jpa.getBatteryLevel()
            dto.getUpdateRequired() == jpa.isUpdateRequired()
            dto.getConverterType() == ConverterType.DOOR
            dto.getState().size() == 1
            dto.getState()[0].type == "state"

        when:
            jpa.setStatus(DoorStatus.DEACTIVATED)
            dto = doorConverter.toDto()

        then:
            dto.getStatus() == jpa.getStatus()
    }
}
