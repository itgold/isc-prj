package com.iscweb.service.converter.impl;

import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.metadata.DoorStatus;
import com.iscweb.persistence.model.jpa.DoorJpa;
import com.iscweb.persistence.model.jpa.DeviceStateItemJpa;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.converter.SecurityLevel;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

public class DoorConverter extends BaseRegionEntityConverter<DoorDto, DoorJpa> {

    public DoorConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    protected DoorJpa createJpa() {
        return new DoorJpa();
    }

    protected DoorDto createDto() {
        return new DoorDto();
    }

    /**
     * @see super#toJpa()
     */
    @Override
    protected DoorJpa toJpa() {
        DoorJpa result = super.toJpa();
        DoorDto dto = getDto();

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
                if (dto.isModified("connectionStatus")) {
                    result.setConnectionStatus(dto.getConnectionStatus());
                }
                if (dto.isModified("onlineStatus")) {
                    result.setOnlineStatus(dto.getOnlineStatus());
                }
                if (dto.isModified("batteryStatus")) {
                    result.setBatteryStatus(dto.getBatteryStatus());
                }
                if (dto.isModified("tamperStatus")) {
                    result.setTamperStatus(dto.getTamperStatus());
                }
                if (dto.isModified("openingMode")) {
                    result.setOpeningMode(dto.getOpeningMode());
                }
                if (dto.isModified("batteryLevel")) {
                    result.setBatteryLevel(dto.getBatteryLevel());
                }
                if (dto.isModified("updateRequired")) {
                    result.setUpdateRequired(dto.getUpdateRequired());
                }
                if (dto.isModified("lastSyncTime")) {
                    result.setLastSyncTime(dto.getLastSyncTime());
                }

                result.setStatus(dto.getStatus() != null ? dto.getStatus() : DoorStatus.ACTIVATED);
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
    public DoorDto toDto() {
        DoorDto result = super.toDto();
        DoorJpa jpa = getJpa();

        if (getSecurityLevel().gt(SecurityLevel.IDENTITY)) {
            if (getScope().gte(Scope.BASIC)) {
                result.setExternalId(jpa.getExternalId());
                result.setName(jpa.getName());
                result.setDescription(jpa.getDescription());
                result.setType(jpa.getType());
                result.setStatus(jpa.getStatus() != null ? jpa.getStatus() : DoorStatus.ACTIVATED);

                result.setConnectionStatus(jpa.getConnectionStatus());
                result.setOnlineStatus(jpa.getOnlineStatus());
                result.setBatteryStatus(jpa.getBatteryStatus());
                result.setTamperStatus(jpa.getTamperStatus());
                result.setOpeningMode(jpa.getOpeningMode());
                result.setBatteryLevel(jpa.getBatteryLevel());
                result.setUpdateRequired(jpa.isUpdateRequired());
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
