package com.iscweb.service.converter

import com.iscweb.common.model.dto.entity.core.SchoolDistrictDto
import com.iscweb.common.model.metadata.ConverterType
import com.iscweb.common.model.metadata.SchoolDistrictStatus
import com.iscweb.common.util.TrackableDtoProxy
import com.iscweb.persistence.model.jpa.SchoolDistrictJpa
import com.iscweb.service.converter.impl.SchoolDistrictConverter
import spock.lang.Specification

class SchoolDistrictConverterTest extends Specification {

    def "updateSameJpaInstance"() {
        given:
            SchoolDistrictConverter schoolDistrictConverter = new SchoolDistrictConverter(new Convert.ConvertContext())
            SchoolDistrictJpa jpa = new SchoolDistrictJpa()
            schoolDistrictConverter.withJpa(jpa)
            SchoolDistrictDto dto = TrackableDtoProxy.getProxy(new SchoolDistrictDto())
            dto.setName("School_name")
            dto.setContactEmail("School_email")
            dto.setAddress("2340 Jackson street")
            dto.setCity("Los Angeles")
            dto.setState("CA")
            dto.setZipCode("55555")
            dto.setCountry("USA")

            schoolDistrictConverter.setSource(dto)

            SchoolDistrictJpa resultJpa

        when:
            resultJpa = schoolDistrictConverter.toJpa()

        then:
            resultJpa == jpa

        when:
            resultJpa = schoolDistrictConverter.toJpa()

        then:
            resultJpa.getName() == dto.getName()
            resultJpa.getContactEmail() == dto.getContactEmail()
            resultJpa.getAddress() == dto.getAddress()
            resultJpa.getCity() == dto.getCity()
            resultJpa.getState() == dto.getState()
            resultJpa.getZipCode() == dto.getZipCode()
            resultJpa.getCountry() == dto.getCountry()
            resultJpa.getStatus() == SchoolDistrictStatus.ACTIVATED
            resultJpa.getConverterType() == ConverterType.SCHOOL_DISTRICT

        when:
            dto.setStatus(SchoolDistrictStatus.DEACTIVATED)
            resultJpa = schoolDistrictConverter.toJpa()

        then:
            resultJpa.getStatus() == dto.getStatus()

        when:
            dto.setName("new_schoolDistrict_name")
            dto.setContactEmail("new_schoolDistrict_email")
            dto.setAddress("new_schoolDistrict_address")
            dto.setCity("new_schoolDistrict_city")
            dto.setState("new_schoolDistrict_state")
            dto.setZipCode("new_schoolDistrict_zipcode")
            dto.setCountry("new_schoolDistrict_country")
            resultJpa = schoolDistrictConverter.toJpa()

        then:
            resultJpa.getName() == dto.getName()
            resultJpa.getContactEmail() == dto.getContactEmail()
            resultJpa.getAddress() == dto.getAddress()
            resultJpa.getCity() == dto.getCity()
            resultJpa.getState() == dto.getState()
            resultJpa.getZipCode() == dto.getZipCode()
            resultJpa.getCountry() == dto.getCountry()
            resultJpa.getStatus() == SchoolDistrictStatus.DEACTIVATED
            resultJpa.getConverterType() == ConverterType.SCHOOL_DISTRICT

        when:
            dto.setStatus(null)
            resultJpa = schoolDistrictConverter.toJpa()

        then:
            resultJpa.getStatus() == SchoolDistrictStatus.ACTIVATED

    }

    def "generateNewJpaInstance"() {
        given:
            SchoolDistrictConverter schoolDistrictConverter = new SchoolDistrictConverter(new Convert.ConvertContext())
            SchoolDistrictJpa jpa
            SchoolDistrictDto dto = new SchoolDistrictDto()
            dto.setName("SchoolDistrict_name")
            dto.setContactEmail("SchoolDistrict_email")
            dto.setAddress("150 Fillmore street")
            dto.setCity("Los Angeles")
            dto.setState("CA")
            dto.setZipCode("33222")
            dto.setCountry("USA")

            schoolDistrictConverter.setSource(dto)

        when:
            jpa = schoolDistrictConverter.toJpa()

        then:
            jpa.getName() == dto.getName()
            jpa.getContactEmail() == dto.getContactEmail()
            jpa.getAddress() == dto.getAddress()
            jpa.getCity() == dto.getCity()
            jpa.getState() == dto.getState()
            jpa.getZipCode() == dto.getZipCode()
            jpa.getCountry() == dto.getCountry()
            jpa.getStatus() == SchoolDistrictStatus.ACTIVATED
            jpa.getConverterType() == ConverterType.SCHOOL_DISTRICT

        when:
            dto.setStatus(SchoolDistrictStatus.DEACTIVATED)
            jpa = schoolDistrictConverter.toJpa()

        then:
            jpa.getStatus() == dto.getStatus()
    }

    def "toDto"() {
        given:
            SchoolDistrictConverter SchoolDistrictConverter = new SchoolDistrictConverter(new Convert.ConvertContext())
            SchoolDistrictDto dto
            SchoolDistrictJpa jpa = new SchoolDistrictJpa()

            jpa.setName("name")
            jpa.setContactEmail("contact_email")
            jpa.setAddress("address")
            jpa.setCity("San Francisco")
            jpa.setState("CA")
            jpa.setZipCode("94777")
            jpa.setCountry("USA")
            jpa.setStatus(SchoolDistrictStatus.DEACTIVATED)

            SchoolDistrictConverter.setSource(jpa)

        when:
            dto = SchoolDistrictConverter.toDto()

        then:
            dto.getName() == jpa.getName()
            dto.getContactEmail() == jpa.getContactEmail()
            dto.getAddress() == jpa.getAddress()
            dto.getCity() == jpa.getCity()
            dto.getState() == jpa.getState()
            dto.getZipCode() == jpa.getZipCode()
            dto.getCountry() == jpa.getCountry()
            dto.getStatus() == jpa.getStatus()
            dto.getConverterType() == ConverterType.SCHOOL_DISTRICT
    }
}
