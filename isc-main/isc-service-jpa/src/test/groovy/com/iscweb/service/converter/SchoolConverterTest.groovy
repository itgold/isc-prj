package com.iscweb.service.converter

import com.iscweb.common.model.dto.entity.core.SchoolDto
import com.iscweb.common.model.metadata.ConverterType
import com.iscweb.common.model.metadata.SchoolStatus
import com.iscweb.common.util.TrackableDtoProxy
import com.iscweb.persistence.model.jpa.SchoolDistrictJpa
import com.iscweb.persistence.model.jpa.SchoolJpa
import com.iscweb.service.converter.impl.SchoolConverter
import spock.lang.Specification

class SchoolConverterTest extends Specification {

    def "updateSameJpaInstance"() {
        given:
            SchoolConverter schoolConverter = new SchoolConverter(new Convert.ConvertContext())
            SchoolJpa jpa = new SchoolJpa()
            schoolConverter.withJpa(jpa)
            SchoolDto dto = TrackableDtoProxy.getProxy(new SchoolDto())
            dto.setName("School_name")
            dto.setContactEmail("School_email")
            dto.setAddress("2340 Jackson street")
            dto.setCity("Los Angeles")
            dto.setState("CA")
            dto.setZipCode("55555")
            dto.setCountry("USA")

            schoolConverter.setSource(dto)

            SchoolJpa resultJpa

        when:
            resultJpa = schoolConverter.toJpa()

        then:
            resultJpa == jpa

        when:
            resultJpa = schoolConverter.toJpa()

        then:
            resultJpa.getName() == dto.getName()
            resultJpa.getContactEmail() == dto.getContactEmail()
            resultJpa.getAddress() == dto.getAddress()
            resultJpa.getCity() == dto.getCity()
            resultJpa.getState() == dto.getState()
            resultJpa.getZipCode() == dto.getZipCode()
            resultJpa.getCountry() == dto.getCountry()
            resultJpa.getStatus() == SchoolStatus.ACTIVATED
            resultJpa.getConverterType() == ConverterType.SCHOOL

        when:
            dto.setStatus(SchoolStatus.DEACTIVATED)
            resultJpa = schoolConverter.toJpa()

        then:
            resultJpa.getStatus() == dto.getStatus()

        when:
            dto.setName("new_school_name")
            dto.setContactEmail("new_school_email")
            dto.setAddress("new_school_address")
            dto.setCity("new_school_city")
            dto.setState("new_school_state")
            dto.setZipCode("new_school_zipcode")
            dto.setCountry("new_school_country")
            resultJpa = schoolConverter.toJpa()

        then:
            resultJpa.getName() == dto.getName()
            resultJpa.getContactEmail() == dto.getContactEmail()
            resultJpa.getAddress() == dto.getAddress()
            resultJpa.getCity() == dto.getCity()
            resultJpa.getState() == dto.getState()
            resultJpa.getZipCode() == dto.getZipCode()
            resultJpa.getCountry() == dto.getCountry()
            resultJpa.getStatus() == SchoolStatus.DEACTIVATED
            resultJpa.getConverterType() == ConverterType.SCHOOL

        when:
            dto.setStatus(null)
            resultJpa = schoolConverter.toJpa()

        then:
            resultJpa.getStatus() == SchoolStatus.ACTIVATED

    }

    def "generateNewJpaInstance"() {
        given:
            SchoolConverter schoolConverter = new SchoolConverter(new Convert.ConvertContext())
            SchoolJpa jpa
            SchoolJpa anotherCallJpa
            SchoolDto dto = new SchoolDto()
            dto.setName("School_name")
            dto.setContactEmail("School_email")
            dto.setAddress("2340 Jackson street")
            dto.setCity("Los Angeles")
            dto.setState("CA")
            dto.setZipCode("55555")
            dto.setCountry("USA")

            schoolConverter.setSource(dto)

        when:
            jpa = schoolConverter.toJpa()

        then:
            jpa.getName() == dto.getName()
            jpa.getContactEmail() == dto.getContactEmail()
            jpa.getAddress() == dto.getAddress()
            jpa.getCity() == dto.getCity()
            jpa.getState() == dto.getState()
            jpa.getZipCode() == dto.getZipCode()
            jpa.getCountry() == dto.getCountry()
            jpa.getStatus() == SchoolStatus.ACTIVATED
            jpa.getConverterType() == ConverterType.SCHOOL

        when:
            dto.setStatus(SchoolStatus.DEACTIVATED)
            anotherCallJpa = schoolConverter.toJpa()

        then:
            anotherCallJpa != jpa
            anotherCallJpa.getStatus() == dto.getStatus()
    }

    def "toDto"() {
        given:
            SchoolConverter SchoolConverter = new SchoolConverter(new Convert.ConvertContext())
            SchoolDto dto
            SchoolJpa jpa = new SchoolJpa()
            SchoolDistrictJpa jpaSchoolDistrict = new SchoolDistrictJpa()
            jpa.setName("School_name")
            jpa.setContactEmail("contact_email")
            jpa.setAddress("address")
            jpa.setCity("San Francisco")
            jpa.setState("CA")
            jpa.setZipCode("94777")
            jpa.setCountry("USA")
            jpa.setStatus(SchoolStatus.DEACTIVATED)
            jpa.setSchoolDistrict(jpaSchoolDistrict)

            SchoolConverter.setSource(jpa)

        when:
            dto = SchoolConverter.toDto()

        then:
            dto.getName() == jpa.getName()
            dto.getContactEmail() == jpa.getContactEmail()
            dto.getAddress() == jpa.getAddress()
            dto.getCity() == jpa.getCity()
            dto.getState() == jpa.getState()
            dto.getZipCode() == jpa.getZipCode()
            dto.getCountry() == jpa.getCountry()
            dto.getStatus() == jpa.getStatus()
            //dto.getSchoolDistrict() == jpa.getSchoolDistrict()
            dto.getConverterType() == ConverterType.SCHOOL

            /*Here should be a test for SchoolDistrict property; not sure how to do that
            when:
            jpa.setSchoolDistrict(jpaSchoolDistrict)
            dto = SchoolConverter.toDto()

            then:
            dto.getSchoolDistrict() == jpa.getSchoolDistrict()*/
    }
}
