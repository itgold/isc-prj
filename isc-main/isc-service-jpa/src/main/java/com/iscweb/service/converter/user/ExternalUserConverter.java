package com.iscweb.service.converter.user;

import com.iscweb.common.model.dto.entity.core.ExternalUserDto;
import com.iscweb.persistence.model.jpa.ExternalUserJpa;
import com.iscweb.service.converter.BaseConverter;
import com.iscweb.service.converter.Convert;

/**
 * External user model converter.
 * Converts from {@link ExternalUserJpa} to {@link ExternalUserDto} and vise versa.
 */
public class ExternalUserConverter extends BaseConverter<ExternalUserDto, ExternalUserJpa> {

    public ExternalUserConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    @Override
    protected ExternalUserJpa createJpa() {
        return new ExternalUserJpa();
    }

    @Override
    protected ExternalUserDto createDto() {
        return new ExternalUserDto();
    }

    @Override
    protected ExternalUserJpa toJpa() {
        ExternalUserJpa result = super.toJpa();
        ExternalUserDto dto = getDto();

        if (dto.getRowId() != null) {
            result.setId(dto.getRowId());
        }
        if (dto.getSource() != null) {
            result.setSource(dto.getSource());
        }
        if (dto.getExternalId() != null) {
            result.setExternalId(dto.getExternalId());
        }

        if (dto.isModified("title")) {
            result.setTitle(dto.getTitle());
        }
        if (dto.isModified("firstName")) {
            result.setFirstName(dto.getFirstName());
        }
        if (dto.isModified("lastName")) {
            result.setLastName(dto.getLastName());
        }
        if (dto.isModified("phoneNumber")) {
            result.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.isModified("status")) {
            result.setStatus(dto.getStatus());
        }
        if (dto.isModified("schoolSite")) {
            result.setSchoolSite(dto.getSchoolSite());
        }
        if (dto.isModified("officialJobTitle")) {
            result.setOfficialJobTitle(dto.getOfficialJobTitle());
        }
        if (dto.isModified("idFullName")) {
            result.setIdFullName(dto.getIdFullName());
        }
        if (dto.isModified("idNumber")) {
            result.setIdNumber(dto.getIdNumber());
        }
        if (dto.isModified("officeClass")) {
            result.setOfficeClass(dto.getOfficeClass());
        }
        if (dto.isModified("lastSyncTime")) {
            result.setLastSyncTime(dto.getLastSyncTime());
        }

        return result;
    }

    @Override
    protected ExternalUserDto toDto() {
        ExternalUserDto result = super.toDto();
        ExternalUserJpa user = getJpa();

        result.setExternalId(user.getExternalId());
        result.setSource(user.getSource());
        result.setTitle(user.getTitle());
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        result.setPhoneNumber(user.getPhoneNumber());
        result.setStatus(user.getStatus());
        result.setSchoolSite(user.getSchoolSite());
        result.setOfficialJobTitle(user.getOfficialJobTitle());
        result.setIdFullName(user.getIdFullName());
        result.setIdNumber(user.getIdNumber());
        result.setOfficeClass(user.getOfficeClass());
        result.setLastSyncTime(user.getLastSyncTime());

        return result;
    }
}
