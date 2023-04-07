package com.iscweb.service.converter.impl;

import com.iscweb.common.model.dto.entity.core.SafetyDto;
import com.iscweb.common.model.metadata.SafetyStatus;
import com.iscweb.persistence.model.jpa.SafetyJpa;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.converter.SecurityLevel;

public class SafetyConverter extends BaseRegionEntityConverter<SafetyDto, SafetyJpa> {

    public SafetyConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    protected SafetyJpa createJpa() {
        return new SafetyJpa();
    }

    protected SafetyDto createDto() {
        return new SafetyDto();
    }

    /**
     * @see super#toJpa()
     */
    @Override
    protected SafetyJpa toJpa() {
        SafetyJpa result = super.toJpa();
        SafetyDto dto = getDto();
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

                result.setStatus(dto.getStatus() != null ? dto.getStatus() : SafetyStatus.ACTIVATED);
            }
        }
        return result;
    }

    /**
     * @see super#toDto()
     */
    @Override
    public SafetyDto toDto() {
        SafetyDto result = super.toDto();
        SafetyJpa jpa = getJpa();

        if (getSecurityLevel().gt(SecurityLevel.IDENTITY)) {
            if (getScope().gte(Scope.BASIC)) {
                result.setExternalId(jpa.getExternalId());
                result.setName(jpa.getName());
                result.setDescription(jpa.getDescription());
                result.setType(jpa.getType());
                result.setStatus(jpa.getStatus() != null ? jpa.getStatus() : SafetyStatus.ACTIVATED);
                result.setLastSyncTime(jpa.getLastSyncTime());
            }
        }
        return result;
    }

}
