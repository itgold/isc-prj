package com.iscweb.service.converter.impl;

import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.model.metadata.CameraStatus;
import com.iscweb.common.model.metadata.CameraType;
import com.iscweb.persistence.model.jpa.CameraJpa;
import com.iscweb.persistence.model.jpa.DeviceStateItemJpa;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.converter.SecurityLevel;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

public class CameraConverter extends BaseRegionEntityConverter<CameraDto, CameraJpa> {

    public CameraConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    protected CameraJpa createJpa() {
        return new CameraJpa();
    }

    protected CameraDto createDto() {
        return new CameraDto();
    }

    /**
     * @see super#toJpa()
     */
    @Override
    protected CameraJpa toJpa() {
        CameraJpa result = super.toJpa();
        CameraDto dto = getDto();

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
                if (dto.isModified("lastSyncTime")) {
                    result.setLastSyncTime(dto.getLastSyncTime());
                }
                result.setType(dto.getType() != null ? dto.getType() : CameraType.VIDEO);
                result.setStatus(dto.getStatus() != null ? dto.getStatus() : CameraStatus.ACTIVATED);
            }

            if (getScope().gte(Scope.METADATA) && dto.isModified("state")) {
                result.getState().clear();

                if (!CollectionUtils.isEmpty(dto.getState())) {
                    result.getState().addAll(dto.getState()
                            .stream()
                            .map(stateItem -> new DeviceStateItemJpa(stateItem.getType(), stateItem.getValue(), stateItem.getUpdated()))
                            .collect(Collectors.toSet()));
                }
            }
        }

        return result;
    }

    /**
     * @see super#toDto()
     */
    @Override
    public CameraDto toDto() {
        CameraDto result = super.toDto();
        CameraJpa jpa = getJpa();

        if (getSecurityLevel().gt(SecurityLevel.IDENTITY)) {
            if (getScope().gte(Scope.BASIC)) {
                result.setExternalId(jpa.getExternalId());
                result.setType(jpa.getType());
                result.setStatus(jpa.getStatus() != null ? jpa.getStatus() : CameraStatus.ACTIVATED);
                result.setDescription(jpa.getDescription());
                result.setName(jpa.getName());
                result.setLastSyncTime(jpa.getLastSyncTime());
            }

            if (getScope().gte(Scope.METADATA) && !CollectionUtils.isEmpty(jpa.getState())) {
                result.setState(jpa.getState()
                        .stream()
                        .map(state -> new DeviceStateItemDto(state.getName(), state.getValue(), state.getUpdated()))
                        .collect(Collectors.toSet())
                );
            }
        }

        return result;
    }
}
