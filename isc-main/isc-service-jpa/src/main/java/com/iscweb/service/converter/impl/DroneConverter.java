package com.iscweb.service.converter.impl;

import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.DroneDto;
import com.iscweb.common.model.metadata.DroneStatus;
import com.iscweb.persistence.model.jpa.DeviceStateItemJpa;
import com.iscweb.persistence.model.jpa.DroneJpa;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.converter.SecurityLevel;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

public class DroneConverter extends BaseRegionEntityConverter<DroneDto, DroneJpa> {

    public DroneConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    protected DroneJpa createJpa() {
        return new DroneJpa();
    }

    protected DroneDto createDto() {
        return new DroneDto();
    }

    /**
     * @see super#toJpa()
     */
    @Override
    protected DroneJpa toJpa() {
        DroneJpa result = super.toJpa();
        DroneDto dto = getDto();

        if (getSecurityLevel().gt(SecurityLevel.IDENTITY)) {
            if (getScope().gte(Scope.BASIC)) {
                if (dto.getExternalId() != null) {
                    result.setExternalId(dto.getExternalId());
                }
                if (dto.isModified("type")) {
                    result.setType(dto.getType());
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
                result.setStatus(dto.getStatus() != null ? dto.getStatus() : DroneStatus.ACTIVATED);
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
    @SuppressWarnings("Duplicates")
    @Override
    public DroneDto toDto() {
        DroneDto result = super.toDto();
        DroneJpa jpa = getJpa();

        if (getSecurityLevel().gt(SecurityLevel.IDENTITY)) {
            if (getScope().gte(Scope.BASIC)) {
                result.setExternalId(jpa.getExternalId());
                result.setType(jpa.getType());
                result.setStatus(jpa.getStatus() != null ? jpa.getStatus() : DroneStatus.ACTIVATED);
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
