package com.iscweb.service.converter.impl;

import com.google.common.collect.Sets;
import com.iscweb.common.model.dto.entity.core.SchoolDistrictDto;
import com.iscweb.common.model.dto.entity.core.SchoolDto;
import com.iscweb.common.model.metadata.SchoolStatus;
import com.iscweb.persistence.model.jpa.SchoolDistrictJpa;
import com.iscweb.persistence.model.jpa.SchoolJpa;
import com.iscweb.persistence.repositories.impl.RegionJpaRepository;
import com.iscweb.persistence.repositories.impl.SchoolDistrictJpaRepository;
import com.iscweb.service.converter.BaseConverter;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.converter.SecurityLevel;
import org.apache.commons.lang3.StringUtils;

public class SchoolConverter extends BaseConverter<SchoolDto, SchoolJpa> {

    public SchoolConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    protected SchoolJpa createJpa() {
        return new SchoolJpa();
    }

    protected SchoolDto createDto() {
        return new SchoolDto();
    }

    /**
     * @see super#toJpa()
     */
    @Override
    protected SchoolJpa toJpa() {
        SchoolJpa result = super.toJpa();
        SchoolDto dto = getDto();

        if (getSecurityLevel().gt(SecurityLevel.IDENTITY)) {
            if (getScope().gte(Scope.BASIC)) {
                if (dto.isModified("name")) {
                    result.setName(dto.getName());
                }
                if (dto.isModified("contactEmail")) {
                    result.setContactEmail(dto.getContactEmail());
                }
                if (dto.isModified("address")) {
                    result.setAddress(dto.getAddress());
                }
                if (dto.isModified("city")) {
                    result.setCity(dto.getCity());
                }
                if (dto.isModified("state")) {
                    result.setState(dto.getState());
                }
                if (dto.isModified("zipCode")) {
                    result.setZipCode(dto.getZipCode());
                }
                if (dto.isModified("country")) {
                    result.setCountry(dto.getCountry());
                }

                result.setStatus(dto.getStatus() != null ? dto.getStatus() : SchoolStatus.ACTIVATED);
            }

            if (getScope().gte(Scope.ALL)) {
                if (dto.getRegion() != null && !StringUtils.isEmpty(dto.getRegion().getId())) {
                    RegionJpaRepository regionRepository = getConvertContext().getRegionRepository();
                    result.setRegion(regionRepository.findByGuid(dto.getRegion().getId()));
                }

                SchoolDistrictDto schoolDistrictDto = dto.getSchoolDistrict();
                if (schoolDistrictDto != null && !StringUtils.isEmpty(schoolDistrictDto.getId())) {
                    SchoolDistrictJpaRepository schoolDistrictRepository = getConvertContext().getSchoolDistrictRepository();
                    SchoolDistrictJpa schoolDistrict = schoolDistrictRepository.findByGuid(schoolDistrictDto.getId());
                    result.setSchoolDistrict(schoolDistrict);

                    //reassigning school region to the new school district region
                    if (schoolDistrict.getRegion() != null && result.getRegion() != null) {
                        result.getRegion().setRegions(Sets.newHashSet(schoolDistrict.getRegion()));
                    }
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
    public SchoolDto toDto() {
        SchoolDto result = super.toDto();
        SchoolJpa jpa = getJpa();

        if (getSecurityLevel().gt(SecurityLevel.IDENTITY)) {
            if (getScope().gte(Scope.BASIC)) {
                result.setName(jpa.getName());
                result.setContactEmail(jpa.getContactEmail());
                result.setAddress(jpa.getAddress());
                result.setCity(jpa.getCity());
                result.setState(jpa.getState());
                result.setZipCode(jpa.getZipCode());
                result.setCountry(jpa.getCountry());
                result.setStatus(jpa.getStatus());
            }

            if (getScope().gte(Scope.ALL)) {
                if (jpa.getRegion() != null) {
                    result.setRegion(Convert.my(jpa.getRegion()).boom());
                }

                if (jpa.getSchoolDistrict() != null) {
                    result.setSchoolDistrict(Convert.my(jpa.getSchoolDistrict()).boom());
                }
            }
        }

        return result;
    }
}
