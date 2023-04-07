package com.iscweb.service.converter.impl;

import com.google.api.client.util.Lists;
import com.google.api.client.util.Maps;
import com.iscweb.common.model.dto.entity.core.RegionDto;
import com.iscweb.common.model.dto.entity.core.RegionPropDto;
import com.iscweb.common.model.metadata.RegionStatus;
import com.iscweb.common.model.metadata.RegionType;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.util.GeoUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class RegionConverter extends BaseRegionEntityConverter<RegionDto, RegionJpa> {

    public RegionConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    protected RegionJpa createJpa() {
        return new RegionJpa();
    }

    protected RegionDto createDto() {
        return new RegionDto();
    }

    /**
     * @see super#toJpa()
     */
    @Override
    protected RegionJpa toJpa() {
        RegionJpa result = super.toJpa();
        RegionDto dto = getDto();

        if (dto.isModified("name")) {
            result.setName(dto.getName());
        }

        if (getScope().gt(Scope.IDENTITY)) {
            if (getScope().gte(Scope.BASIC)) {
                if (dto.isModified("description")) {
                    result.setDescription(dto.getDescription());
                }

                if (dto.isModified("type")) {
                    result.setType(dto.getType() != null ? dto.getType() : RegionType.UNKNOWN);
                } else if (result.getType() == null) {
                    result.setType(RegionType.UNKNOWN);
                }

                if (dto.isModified("subType")) {
                    result.setSubType(dto.getSubType());
                }
                result.setStatus(dto.getStatus() != null ? dto.getStatus() : RegionStatus.ACTIVATED);
            }

            if (getScope().gte(Scope.METADATA)) {
                if (dto.isModified("props")) {
                    if (result.getProps() != null) {
                        result.getProps().clear();
                    } else if (!CollectionUtils.isEmpty(dto.getProps())) {
                        result.setProps(Maps.newHashMap());
                    }

                    if (!CollectionUtils.isEmpty(dto.getProps())) {
                        dto.getProps().forEach(prop -> result.getProps().put(prop.getKey(), prop.getValue()));
                    }
                }

                if (dto.isModified("geoBoundaries")) {
                    result.setGeoBoundaries(GeoUtils.convert(dto.getGeoBoundaries()));
                }
                if (dto.isModified("geoLocation")) {
                    result.setGeoLocation(GeoUtils.convert(dto.getGeoLocation()));
                }
                if (dto.isModified("geoZoom")) {
                    result.setGeoZoom(dto.getGeoZoom());
                }
                if (dto.isModified("geoRotation")) {
                    result.setGeoRotation(dto.getGeoRotation());
                }
            }
        }

        return result;
    }

    /**
     * @see super#toDto()
     */
    @SuppressWarnings("Duplicates")
    @Override
    public RegionDto toDto() {
        RegionDto result = super.toDto();
        RegionJpa jpa = getJpa();

        result.setName(jpa.getName());

        if (getScope().gt(Scope.IDENTITY)) {

            if (getScope().gte(Scope.BASIC)) {
                result.setStatus(jpa.getStatus());
                result.setType(jpa.getType());
                result.setSubType(jpa.getSubType());
                result.setDescription(jpa.getDescription());
            }

            if (getScope().gte(Scope.METADATA)) {
                if (jpa.getGeoBoundaries() != null) {
                    result.setGeoBoundaries(GeoUtils.convert(jpa.getGeoBoundaries()));
                }
                if (jpa.getGeoLocation() != null) {
                    result.setGeoLocation(GeoUtils.convert(jpa.getGeoLocation()));
                }
                if (jpa.getGeoZoom() != null) {
                    result.setGeoZoom(jpa.getGeoZoom());
                }
                if (jpa.getGeoRotation() != null) {
                    result.setGeoRotation(jpa.getGeoRotation());
                }

                if (jpa.getProps() != null && jpa.getProps().size() > 0) {
                    List<RegionPropDto> props = Lists.newArrayList();
                    jpa.getProps().entrySet().forEach(entry -> props.add(new RegionPropDto(entry.getKey(), entry.getValue())));
                    result.setProps(props);
                }
            }
        }


        return result;
    }
}
