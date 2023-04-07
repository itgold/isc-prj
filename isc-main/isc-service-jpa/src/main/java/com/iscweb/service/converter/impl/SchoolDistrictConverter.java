package com.iscweb.service.converter.impl;

import com.iscweb.common.model.dto.entity.core.SchoolDistrictDto;
import com.iscweb.common.model.metadata.SchoolDistrictStatus;
import com.iscweb.persistence.model.jpa.SchoolDistrictJpa;
import com.iscweb.persistence.repositories.impl.RegionJpaRepository;
import com.iscweb.service.converter.BaseConverter;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.converter.SecurityLevel;
import org.apache.commons.lang3.StringUtils;

public class SchoolDistrictConverter extends BaseConverter<SchoolDistrictDto, SchoolDistrictJpa> {

    public SchoolDistrictConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    protected SchoolDistrictJpa createJpa() {
        return new SchoolDistrictJpa();
    }

    protected SchoolDistrictDto createDto() {
        return new SchoolDistrictDto();
    }

    /**
     * @see super#toJpa()
     */
    @Override
    protected SchoolDistrictJpa toJpa() {
        SchoolDistrictJpa result = super.toJpa();
        SchoolDistrictDto dto = getDto();

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
                result.setStatus(dto.getStatus() != null ? dto.getStatus() : SchoolDistrictStatus.ACTIVATED);
            }

            if (getScope().gte(Scope.ALL)) {

                if (dto.getRegion() != null && !StringUtils.isEmpty(dto.getRegion().getId())) {
                    RegionJpaRepository regionRepository = getConvertContext().getRegionRepository();
                    result.setRegion(regionRepository.findByGuid(dto.getRegion().getId()));
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
    public SchoolDistrictDto toDto() {
        SchoolDistrictDto result = super.toDto();
        SchoolDistrictJpa jpa = getJpa();

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
                    result.setRegion(Convert.my(jpa.getRegion()).scope(Scope.IDENTITY).boom());
                }
            }
        }

        return result;
    }
}
