package com.iscweb.service.converter

import com.iscweb.common.model.dto.entity.core.TagDto
import com.iscweb.common.util.TrackableDtoProxy
import com.iscweb.persistence.model.jpa.TagJpa
import com.iscweb.service.converter.impl.TagConverter
import spock.lang.Specification

class TagConverterTest extends Specification {

    def "updateSameJpaInstance"() {
        given:
            TagConverter tagConverter = new TagConverter(new Convert.ConvertContext())
            TagJpa jpa = new TagJpa()
            tagConverter.withJpa(jpa)
            TagDto dto = TrackableDtoProxy.getProxy(new TagDto())
            String tagName = "tag_name"
            dto.setName(tagName)

            tagConverter.setSource(dto)

            TagJpa resultJpa

        when:
            resultJpa = tagConverter.toJpa()

        then:
            resultJpa == jpa

        when:
            dto.setName(null)
            resultJpa = tagConverter.toJpa()

        then:
            resultJpa.getName() == null

        when:
            resultJpa = tagConverter.toJpa()

        then:
            resultJpa.getName() == dto.getName()

        when:
            dto.setName(null)
            resultJpa = tagConverter.toJpa()

        then:
            resultJpa.getName() == dto.getName()
    }

    def "generateNewJpaInstance"() {
        given:
            TagConverter tagConverter = new TagConverter(new Convert.ConvertContext())
            TagDto dto = new TagDto()
            String tagName = "tag_name"
            dto.setName(tagName)

            tagConverter.setSource(dto)

            TagJpa resultJpa

        when:
            dto.setName(null)
            resultJpa = tagConverter.toJpa()

        then:
            resultJpa.getName() == null

        when:
            resultJpa = tagConverter.toJpa()

        then:
            resultJpa.getName() == dto.getName()

        when:
            dto.setName(null)
            resultJpa = tagConverter.toJpa()

        then:
            resultJpa.getName() == null
    }

    def "toDto"() {
        given:
            TagConverter tagConverter = new TagConverter(new Convert.ConvertContext())
            TagDto dto
            TagJpa jpa = new TagJpa()
            String tagName = "tag_name"
            jpa.setName(tagName)

            tagConverter.setSource(jpa)

        when:
            dto = tagConverter.toDto()

        then:
            dto.getName() == jpa.getName()

        when:
            jpa.setName(null)
            dto = tagConverter.toDto()

        then:
            dto.getName() == null
    }
}
