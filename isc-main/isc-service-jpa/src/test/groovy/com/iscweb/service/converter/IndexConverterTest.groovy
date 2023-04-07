package com.iscweb.service.converter

import com.iscweb.common.model.dto.entity.IIndexDto
import com.iscweb.common.model.dto.entity.core.ListItemIndexDto
import com.iscweb.common.util.TrackableDtoProxy
import com.iscweb.persistence.model.jpa.IndexJpa
import com.iscweb.service.converter.impl.IndexConverter
import spock.lang.Specification

import java.time.ZoneId
import java.time.ZonedDateTime

class IndexConverterTest extends Specification {

    def "updateSameJpaInstance"() {
        given:
            IndexConverter indexConverter = new IndexConverter(new Convert.ConvertContext())
            IndexJpa jpa = new IndexJpa()
            indexConverter.withJpa(jpa)
            IIndexDto dto = TrackableDtoProxy.getProxy(new ListItemIndexDto())

            ZonedDateTime nowDateTime = ZonedDateTime.now()

            ZoneId zoneId = ZoneId.of("UTC+1")
            ZonedDateTime oldDateTime =
                    ZonedDateTime.of(2020, 11, 30, 23, 45, 59, 1234, zoneId)

            indexConverter.setSource(dto)
            indexConverter.scope(Scope.ALL)
            indexConverter.securityLevel(SecurityLevel.ALL)

            IndexJpa resultJpa

        when:
            resultJpa = indexConverter.toJpa()

        then:
            resultJpa == jpa

        when:
            resultJpa = indexConverter.toJpa()

        then:
            resultJpa.getCreated() == null
            resultJpa.getUpdated() == null

        when:
            dto.setCreated(oldDateTime)
            dto.setUpdated(oldDateTime)
            resultJpa = indexConverter.toJpa()

        then:
            resultJpa.getCreated() == oldDateTime
            resultJpa.getCreated() == dto.getCreated()
            resultJpa.getUpdated() == dto.getUpdated()

        when:
            dto.setCreated(null)
            dto.setUpdated(null)
            resultJpa = indexConverter.toJpa()

        then:
            resultJpa.getCreated() == oldDateTime
            resultJpa.getUpdated() == oldDateTime

        when:
            dto.setCreated(nowDateTime)
            dto.setUpdated(nowDateTime)
            resultJpa = indexConverter.toJpa()

        then:
            resultJpa.getCreated() == nowDateTime
            resultJpa.getUpdated() == nowDateTime
    }

    def "generateNewJpaInstance"() {

        given:
            IndexConverter indexConverter = new IndexConverter(new Convert.ConvertContext())
            IIndexDto dto = new ListItemIndexDto()

            ZonedDateTime nowDateTime = ZonedDateTime.now()

            indexConverter.setSource(dto)
            indexConverter.scope(Scope.ALL)
            indexConverter.securityLevel(SecurityLevel.ALL)
            IndexJpa resultJpa

        when:
            dto.setCreated(nowDateTime)
            dto.setUpdated(nowDateTime)
            resultJpa = indexConverter.toJpa()

        then:
            resultJpa.getCreated() == nowDateTime
            resultJpa.getCreated() == dto.getCreated()
            resultJpa.getUpdated() == dto.getUpdated()

        when:
            dto.setCreated(null)
            dto.setUpdated(null)
            resultJpa = indexConverter.toJpa()

        then:
            resultJpa.getCreated() == null
            resultJpa.getUpdated() == null
    }

    def "toDto"() {
        given:
            IndexConverter indexConverter = new IndexConverter(new Convert.ConvertContext())
            IIndexDto result1
            IIndexDto result2

        when:
            result1 = indexConverter.toDto()
            result2 = indexConverter.toDto()

        then:
            !result1
            !result2
    }
}
