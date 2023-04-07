package com.iscweb.service.converter

import com.iscweb.common.model.dto.entity.core.CameraDto
import com.iscweb.common.model.metadata.CameraStatus
import com.iscweb.common.model.metadata.CameraType
import com.iscweb.common.model.metadata.ConverterType
import com.iscweb.common.util.TrackableDtoProxy
import com.iscweb.persistence.model.jpa.CameraJpa
import com.iscweb.persistence.model.jpa.DeviceStateItemJpa
import com.iscweb.service.converter.impl.CameraConverter
import spock.lang.Specification

class CameraConverterTest extends Specification {

    def "updateSameJpaInstance"() {
        given:
            CameraConverter cameraConverter = new CameraConverter(new Convert.ConvertContext())
            CameraJpa jpa = new CameraJpa()
            cameraConverter.withJpa(jpa)
            CameraDto dto = TrackableDtoProxy.getProxy(new CameraDto())

            String externalId = "20cf5093-4245-468d-b9f1-069fcf7932a1"
            dto.setExternalId(externalId)
            dto.setName("Camera_name")
            dto.setDescription("Camera_principal_office")

            cameraConverter.setSource(dto)

            CameraJpa resultJpa

        when:
            resultJpa = cameraConverter.toJpa()

        then:
            resultJpa == jpa

        when:
            resultJpa = cameraConverter.toJpa()

        then:
            resultJpa.getExternalId() == dto.getExternalId()
            resultJpa.getName() == dto.getName()
            resultJpa.getDescription() == dto.getDescription()
            resultJpa.getType() == CameraType.VIDEO
            resultJpa.getStatus() == CameraStatus.ACTIVATED
            resultJpa.getConverterType() == ConverterType.CAMERA

        when:
            dto.setExternalId(null)
            resultJpa = cameraConverter.toJpa()

        then:
            resultJpa.getExternalId() == externalId
            resultJpa == jpa

        when:
            //not sure how to check when camera type is modified: there is only one camera type defined
            dto.setType(CameraType.VIDEO)
            dto.setStatus(CameraStatus.DEACTIVATED)
            resultJpa = cameraConverter.toJpa()

        then:
            resultJpa.getType() == dto.getType()
            resultJpa.getStatus() == dto.getStatus()

        when:
            dto.setName("new_camera_name")
            dto.setDescription("new_camera_description")
            resultJpa = cameraConverter.toJpa()

        then:
            resultJpa.getName() == dto.getName()
            resultJpa.getDescription() == dto.getDescription()

        when:
            dto.setStatus(null)
            resultJpa = cameraConverter.toJpa()

        then:
            resultJpa.getStatus() == CameraStatus.ACTIVATED
    }

    def "generateNewJpaInstance"() {
        CameraConverter cameraConverter = new CameraConverter(new Convert.ConvertContext())
        CameraJpa jpa
        CameraJpa anotherCallJpa
        CameraDto dto = new CameraDto()
        dto.setExternalId("20cf7890-4245-468d-b9f1-069fcf7932a1")
        dto.setName("Camera_name")
        dto.setDescription("Camera_principal_office")

        cameraConverter.setSource(dto)

        when:
            jpa = cameraConverter.toJpa()

        then:
            jpa.getExternalId() == dto.getExternalId()
            jpa.getName() == dto.getName()
            jpa.getDescription() == dto.getDescription()
            jpa.getType() == CameraType.VIDEO
            jpa.getStatus() == CameraStatus.ACTIVATED
            jpa.getConverterType() == ConverterType.CAMERA

        when:
            anotherCallJpa = cameraConverter.toJpa()

        then:
            anotherCallJpa != jpa
            anotherCallJpa.getExternalId() == anotherCallJpa.getExternalId()
            anotherCallJpa.getName() == anotherCallJpa.getName()
            anotherCallJpa.getDescription() == anotherCallJpa.getDescription()
            anotherCallJpa.getType() == CameraType.VIDEO
            anotherCallJpa.getStatus() == CameraStatus.ACTIVATED
            anotherCallJpa.getConverterType() == ConverterType.CAMERA
    }

    def "toDto"() {
        given:
            CameraConverter cameraConverter = new CameraConverter(new Convert.ConvertContext())
            CameraDto dto
            CameraJpa jpa = new CameraJpa()
            jpa.setExternalId("20cf5093-4245-468d-b9f1-069fcf7932a1")
            jpa.setDescription("description")
            jpa.setName("name")
            jpa.setType(CameraType.VIDEO)

            DeviceStateItemJpa stateItemJpa = new DeviceStateItemJpa()
            stateItemJpa.setName("state")
            Set<DeviceStateItemJpa> statesSet = new HashSet()
            statesSet.add(stateItemJpa)
            jpa.setState(statesSet)

            cameraConverter.setSource(jpa)
            cameraConverter.scope(Scope.METADATA)

        when:
            dto = cameraConverter.toDto()

        then:
            dto.getExternalId() == jpa.getExternalId()
            dto.getName() == jpa.getName()
            dto.getDescription() == jpa.getDescription()
            dto.getType() == jpa.getType()
            dto.getStatus() == CameraStatus.ACTIVATED
            dto.getConverterType() == ConverterType.CAMERA
            dto.getState().size() == 1
            dto.getState()[0].getType() == "state"

        when:
            jpa.setStatus(CameraStatus.DEACTIVATED)
            dto = cameraConverter.toDto()

        then:
            dto.getStatus() == jpa.getStatus()
    }
}
