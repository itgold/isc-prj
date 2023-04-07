package com.iscweb.service.converter.impl;

import com.iscweb.common.model.dto.entity.core.UtilityDto;
import com.iscweb.common.model.metadata.UtilityStatus;
import com.iscweb.persistence.model.jpa.UtilityJpa;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.converter.SecurityLevel;

/**
 * UtilityConverter converts UtilityJpa to UtilityDto and vice versa.
 */
public class UtilityConverter extends BaseRegionEntityConverter<UtilityDto, UtilityJpa> {

    public UtilityConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    protected UtilityJpa createJpa() {
        return new UtilityJpa();
    }

    protected UtilityDto createDto() {
        return new UtilityDto();
    }

    /**
     * @see super#toJpa()
     */
    @Override
    protected UtilityJpa toJpa() {
        UtilityJpa result = super.toJpa();
        UtilityDto dto = getDto();
        if (getSecurityLevel().gt(SecurityLevel.IDENTITY)) {
            if (getScope().gte(Scope.BASIC)) {
                if (dto.getExternalId() != null) {
                    result.setExternalId(dto.getExternalId());
                }
                if (dto.isModified("name")) {
                    result.setName(dto.getName());
                }
                if (dto.isModified("description")) {
                    result.setDescription(dto.getDescription());
                }
                if (dto.isModified("type")) {
                    result.setType(dto.getType());
                }
                if (dto.isModified("lastSyncTime")) {
                    result.setLastSyncTime(dto.getLastSyncTime());
                }

                result.setStatus(dto.getStatus() != null ? dto.getStatus() : UtilityStatus.ACTIVATED);
            }
        }
        return result;
    }

    /**
     * @see super#toDto()
     */
    @Override
    public UtilityDto toDto() {
        UtilityDto result = super.toDto();
        UtilityJpa jpa = getJpa();

        if (getSecurityLevel().gt(SecurityLevel.IDENTITY)) {
            if (getScope().gte(Scope.BASIC)) {
                result.setExternalId(jpa.getExternalId());
                result.setName(jpa.getName());
                result.setDescription(jpa.getDescription());
                result.setType(jpa.getType());
                result.setStatus(jpa.getStatus() != null ? jpa.getStatus() : UtilityStatus.ACTIVATED);
                result.setLastSyncTime(jpa.getLastSyncTime());
            }
        }
        return result;
    }

}
