package com.iscweb.service.converter.impl;

import com.iscweb.common.model.dto.entity.core.AlertDto;
import com.iscweb.persistence.model.jpa.AlertJpa;
import com.iscweb.service.converter.BaseConverter;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.SecurityLevel;

public class AlertConverter extends BaseConverter<AlertDto, AlertJpa> {

    public AlertConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    @Override
    protected AlertJpa createJpa() {
        return new AlertJpa();
    }

    @Override
    protected AlertDto createDto() {
        return new AlertDto();
    }

    /**
     * @see super#toJpa()
     */
    @Override
    protected AlertJpa toJpa() {
        AlertDto dto = getDto();
        AlertJpa result = super.toJpa();

        result.setTriggerId(dto.getTriggerId());
        result.setDeviceId(dto.getDeviceId());
        result.setDeviceType(dto.getDeviceType());

        if (getSecurityLevel().gt(SecurityLevel.IDENTITY)) {
            if (dto.isModified("severity")) {
                result.setSeverity(dto.getSeverity());
            }
            if (dto.isModified("count")) {
                result.setCount(dto.getCount());
            }
            if (dto.isModified("status")) {
                result.setStatus(dto.getStatus());
            }
            if (dto.isModified("eventId")) {
                result.setEventId(dto.getEventId());
            }
            if (dto.isModified("schoolId")) {
                result.setSchoolId(dto.getSchoolId());
            }
            if (dto.isModified("districtId")) {
                result.setDistrictId(dto.getDistrictId());
            }
            if (dto.isModified("code")) {
                result.setCode(dto.getCode());
            }
            if (dto.isModified("description")) {
                result.setDescription(dto.getDescription());
            }
        }

        return result;
    }

    /**
     * @see super#toDto()
     */
    @Override
    protected AlertDto toDto() {
        AlertJpa jpa = getJpa();
        AlertDto result = super.toDto();

        result.setTriggerId(jpa.getTriggerId());
        result.setDeviceId(jpa.getDeviceId());
        result.setDeviceType(jpa.getDeviceType());
        result.setSeverity(jpa.getSeverity());
        result.setCount(jpa.getCount());
        result.setStatus(jpa.getStatus());
        result.setEventId(jpa.getEventId());
        result.setSchoolId(jpa.getSchoolId());
        result.setDistrictId(jpa.getDistrictId());
        result.setCode(jpa.getCode());
        result.setDescription(jpa.getDescription());

        return result;
    }
}
