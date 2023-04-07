package com.iscweb.service.converter

import com.iscweb.common.model.dto.entity.core.SpeakerDto
import com.iscweb.common.model.metadata.ConverterType
import com.iscweb.common.model.metadata.SpeakerStatus
import com.iscweb.common.model.metadata.SpeakerType
import com.iscweb.common.util.TrackableDtoProxy
import com.iscweb.persistence.model.jpa.SpeakerJpa
import com.iscweb.service.converter.impl.SpeakerConverter
import spock.lang.Specification

class SpeakerConverterTest extends Specification {

    def "updateSameJpaInstance"() {
        given:
            SpeakerConverter speakerConverter = new SpeakerConverter(new Convert.ConvertContext())
            SpeakerJpa jpa = new SpeakerJpa()
            speakerConverter.withJpa(jpa)
            SpeakerDto dto = TrackableDtoProxy.getProxy(new SpeakerDto())

            String externalId = "20cf5093-4245-468d-b9f1-069fcf7932a1"
            dto.setExternalId(externalId)
            dto.setName("Speaker_name")
            dto.setDescription("Speaker_principal_office")

            speakerConverter.setSource(dto)

            SpeakerJpa resultJpa

        when:
            resultJpa = speakerConverter.toJpa()

        then:
            resultJpa == jpa

        when:
            resultJpa = speakerConverter.toJpa()

        then:
            resultJpa.getExternalId() == dto.getExternalId()
            resultJpa.getName() == dto.getName()
            resultJpa.getDescription() == dto.getDescription()
            resultJpa.getType() == SpeakerType.UNKNOWN
            resultJpa.getStatus() == SpeakerStatus.ACTIVATED
            resultJpa.getConverterType() == ConverterType.SPEAKER

        when:
            dto.setExternalId(null)
            resultJpa = speakerConverter.toJpa()

        then:
            resultJpa.getExternalId() == externalId
            resultJpa == jpa

        when:
            dto.setType(SpeakerType.FLOOR)
            dto.setStatus(SpeakerStatus.DEACTIVATED)
            resultJpa = speakerConverter.toJpa()

        then:
            resultJpa.getType() == dto.getType()
            resultJpa.getStatus() == dto.getStatus()

        when:
            dto.setName("new_speaker_name")
            dto.setDescription("new_speaker_description")
            resultJpa = speakerConverter.toJpa()

        then:
            resultJpa.getName() == dto.getName()
            resultJpa.getDescription() == dto.getDescription()

        when:
            dto.setStatus(null)
            dto.setType(null)
            resultJpa = speakerConverter.toJpa()

        then:
            resultJpa.getStatus() == SpeakerStatus.ACTIVATED
            resultJpa.getType() == SpeakerType.UNKNOWN
    }

    def "generateNewJpaInstance"() {
        SpeakerConverter speakerConverter = new SpeakerConverter(new Convert.ConvertContext())
        SpeakerJpa jpa
        SpeakerJpa anotherCallJpa
        SpeakerDto dto = new SpeakerDto()
        dto.setExternalId("20cf7890-4245-468d-b9f1-069fcf7932a1")
        dto.setName("Speaker_name")
        dto.setDescription("Speaker_principal_office")

        speakerConverter.setSource(dto)

        when:
            jpa = speakerConverter.toJpa()

        then:
            jpa.getExternalId() == dto.getExternalId()
            jpa.getName() == dto.getName()
            jpa.getDescription() == dto.getDescription()
            jpa.getType() == SpeakerType.UNKNOWN
            jpa.getStatus() == SpeakerStatus.ACTIVATED
            jpa.getConverterType() == ConverterType.SPEAKER

        when:
            dto.setType(SpeakerType.CEILING)
            dto.setStatus(SpeakerStatus.DEACTIVATED)
            anotherCallJpa = speakerConverter.toJpa()

        then:
            anotherCallJpa != jpa
            anotherCallJpa.getExternalId() == anotherCallJpa.getExternalId()
            anotherCallJpa.getName() == anotherCallJpa.getName()
            anotherCallJpa.getDescription() == anotherCallJpa.getDescription()
            anotherCallJpa.getType() == SpeakerType.CEILING
            anotherCallJpa.getStatus() == SpeakerStatus.DEACTIVATED
            anotherCallJpa.getConverterType() == ConverterType.SPEAKER
    }

    def "toDto"() {
        given:
            SpeakerConverter speakerConverter = new SpeakerConverter(new Convert.ConvertContext())
            SpeakerDto dto
            SpeakerJpa jpa = new SpeakerJpa()
            jpa.setExternalId("20cf5093-4245-468d-b9f1-069fcf7932a1")
            jpa.setType(SpeakerType.CEILING)
            jpa.setDescription("speaker_description")
            jpa.setName("speaker_name")

            speakerConverter.setSource(jpa)

        when:
            dto = speakerConverter.toDto()

        then:
            dto.getExternalId() == jpa.getExternalId()
            dto.getName() == jpa.getName()
            dto.getDescription() == jpa.getDescription()
            dto.getType() == jpa.getType()
            dto.getStatus() == SpeakerStatus.ACTIVATED
            dto.getConverterType() == ConverterType.SPEAKER

        when:
            jpa.setStatus(SpeakerStatus.DEACTIVATED)
            dto = speakerConverter.toDto()

        then:
            dto.getStatus() == jpa.getStatus()
    }
}
