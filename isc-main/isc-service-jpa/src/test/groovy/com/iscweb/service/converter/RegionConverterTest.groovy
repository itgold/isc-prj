package com.iscweb.service.converter

import com.iscweb.common.model.dto.entity.core.GeoPointDto
import com.iscweb.common.model.dto.entity.core.GeoPolygonDto
import com.iscweb.common.model.dto.entity.core.RegionDto
import com.iscweb.common.model.metadata.ConverterType
import com.iscweb.common.model.metadata.RegionStatus
import com.iscweb.common.model.metadata.RegionType
import com.iscweb.common.util.TrackableDtoProxy
import com.iscweb.persistence.model.jpa.RegionJpa
import com.iscweb.service.converter.impl.RegionConverter
import com.iscweb.service.util.GeoUtils
import spock.lang.Specification

class RegionConverterTest extends Specification {

    def "updateSameJpaInstance"() {
        given:
            RegionConverter regionConverter = new RegionConverter(new Convert.ConvertContext())
            RegionJpa jpa = new RegionJpa()
            regionConverter.withJpa(jpa)
            RegionDto dto = TrackableDtoProxy.getProxy(new RegionDto())

            dto.setName("Region_name")
            GeoPointDto geoPointDto1 = new GeoPointDto(0, 0)
            GeoPointDto geoPointDto2 = new GeoPointDto(1, 0)
            GeoPointDto geoPointDto3 = new GeoPointDto(1, 1)
            GeoPointDto geoPointDto4 = new GeoPointDto(0, 0)
            List<GeoPointDto> geoPointDtos = new ArrayList<>()
            geoPointDtos.add(geoPointDto1)
            geoPointDtos.add(geoPointDto2)
            geoPointDtos.add(geoPointDto3)
            geoPointDtos.add(geoPointDto4)
            GeoPolygonDto geoPolygonDto = new GeoPolygonDto(geoPointDtos)
            dto.setGeoBoundaries(geoPolygonDto)

            regionConverter.setSource(dto)
            regionConverter.scope(Scope.METADATA)

            RegionJpa resultJpa

        when:
            resultJpa = regionConverter.toJpa()

        then:
            resultJpa == jpa

        when:
            resultJpa = regionConverter.toJpa()

        then:
            resultJpa.getName() == dto.getName()
            resultJpa.getType() == RegionType.UNKNOWN
            resultJpa.getGeoBoundaries() == GeoUtils.convert(dto.getGeoBoundaries())
            resultJpa.getStatus() == RegionStatus.ACTIVATED
            resultJpa.getConverterType() == ConverterType.REGION

        when:
            dto.setName("new_region_name")
            dto.setStatus(RegionStatus.DEACTIVATED)
            dto.setType(RegionType.FLOOR)
            GeoPointDto newGeoPointDto1 = new GeoPointDto(1, 1)
            GeoPointDto newGeoPointDto2 = new GeoPointDto(2, 1)
            GeoPointDto newGeoPointDto3 = new GeoPointDto(2, 2)
            GeoPointDto newGeoPointDto4 = new GeoPointDto(1, 1)
            List<GeoPointDto> newGeoPointDtos = new ArrayList<>()
            newGeoPointDtos.add(newGeoPointDto1)
            newGeoPointDtos.add(newGeoPointDto2)
            newGeoPointDtos.add(newGeoPointDto3)
            newGeoPointDtos.add(newGeoPointDto4)
            GeoPolygonDto newGeoPolygonDto = new GeoPolygonDto(newGeoPointDtos)
            dto.setGeoBoundaries(newGeoPolygonDto)
            resultJpa = regionConverter.toJpa()

        then:
            resultJpa.getName() == dto.getName()
            resultJpa.getGeoBoundaries() == GeoUtils.convert(dto.getGeoBoundaries())
            resultJpa.getStatus() == dto.getStatus()
            resultJpa.getType() == dto.getType()
            resultJpa.getConverterType() == ConverterType.REGION

        when:
            dto.setType(null)
            resultJpa = regionConverter.toJpa()

        then:
            resultJpa.getType() == RegionType.UNKNOWN
    }

    def "generateNewJpaInstance"() {
        given:
            RegionConverter regionConverter = new RegionConverter(new Convert.ConvertContext())
            RegionDto dto = new RegionDto()
            dto.setName("Region_name")
            GeoPointDto geoPointDto1 = new GeoPointDto(0, 0)
            GeoPointDto geoPointDto2 = new GeoPointDto(1, 0)
            GeoPointDto geoPointDto3 = new GeoPointDto(1, 1)
            GeoPointDto geoPointDto4 = new GeoPointDto(0, 0)
            List<GeoPointDto> geoPointDtos = new ArrayList<>()
            geoPointDtos.add(geoPointDto1)
            geoPointDtos.add(geoPointDto2)
            geoPointDtos.add(geoPointDto3)
            geoPointDtos.add(geoPointDto4)
            GeoPolygonDto geoPolygonDto = new GeoPolygonDto(geoPointDtos)
            dto.setGeoBoundaries(geoPolygonDto)

            regionConverter.setSource(dto)
            regionConverter.scope(Scope.METADATA)

            RegionJpa resultJpa

        when:
            resultJpa = regionConverter.toJpa()

        then:
            resultJpa.getName() == dto.getName()
            resultJpa.getType() == RegionType.UNKNOWN
            resultJpa.getGeoBoundaries() == GeoUtils.convert(dto.getGeoBoundaries())
            resultJpa.getStatus() == RegionStatus.ACTIVATED
            resultJpa.getConverterType() == ConverterType.REGION

        when:
            dto.setType(RegionType.ROOM)
            dto.setStatus(RegionStatus.DEACTIVATED)
            resultJpa = regionConverter.toJpa()

        then:
            resultJpa.getType() == RegionType.ROOM
            resultJpa.getStatus() == RegionStatus.DEACTIVATED

        when:
            dto.setType(null)
            resultJpa = regionConverter.toJpa()

        then:
            resultJpa.getType() == RegionType.UNKNOWN
    }

    def "toDto"() {
        given:
            RegionConverter regionConverter = new RegionConverter(new Convert.ConvertContext())
            RegionDto dto
            RegionJpa jpa = new RegionJpa()
            jpa.setName("region_name")
            jpa.setType(RegionType.BUILDING)
            jpa.setStatus(RegionStatus.DEACTIVATED)
            GeoPointDto geoPointDto1 = new GeoPointDto(0, 0)
            GeoPointDto geoPointDto2 = new GeoPointDto(1, 0)
            GeoPointDto geoPointDto3 = new GeoPointDto(1, 1)
            GeoPointDto geoPointDto4 = new GeoPointDto(0, 0)
            List<GeoPointDto> geoPointDtos = new ArrayList<>()
            geoPointDtos.add(geoPointDto1)
            geoPointDtos.add(geoPointDto2)
            geoPointDtos.add(geoPointDto3)
            geoPointDtos.add(geoPointDto4)
            GeoPolygonDto geoPolygonDto = new GeoPolygonDto(geoPointDtos)
            jpa.setGeoBoundaries(GeoUtils.convert(geoPolygonDto))

            regionConverter.setSource(jpa)
            regionConverter.scope(Scope.METADATA)

        when:
            dto = regionConverter.toDto()

        then:
            dto.getName() == jpa.getName()
            dto.getType() == jpa.getType()
            dto.getStatus() == jpa.getStatus()
            dto.getGeoBoundaries() == geoPolygonDto
            dto.getConverterType() == ConverterType.REGION
    }
}
