package com.iscweb.service.converter.impl;

import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.RadioDto;
import com.iscweb.common.model.metadata.RadioStatus;
import com.iscweb.persistence.model.jpa.DeviceStateItemJpa;
import com.iscweb.persistence.model.jpa.RadioJpa;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.converter.SecurityLevel;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

/**
 * Converter implementation for {@link RadioJpa} objects.
 */
public class RadioConverter extends BaseRegionEntityConverter<RadioDto, RadioJpa> {

    public RadioConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    protected RadioJpa createJpa() {
        return new RadioJpa();
    }

    protected RadioDto createDto() {
        return new RadioDto();
    }

    /**
     * @see super#toJpa()
     */
    @Override
    protected RadioJpa toJpa() {
        RadioJpa result = super.toJpa();
        RadioDto dto = getDto();

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
                if (dto.isModified("batteryLevel")) {
                    result.setBatteryLevel(dto.getBatteryLevel());
                }
                if (dto.isModified("deviceState")) {
                    result.setDeviceState(dto.getDeviceState());
                }
                if (dto.isModified("radioUserId")) {
                    result.setRadioUserId(dto.getRadioUserId());
                }
                if (dto.isModified("gpsAltitude")) {
                    result.setGpsAltitude(dto.getGpsAltitude());
                }
                if (dto.isModified("gpsDirection")) {
                    result.setGpsDirection(dto.getGpsDirection());
                }
                if (dto.isModified("gpsUpdateTime")) {
                    result.setGpsUpdateTime(dto.getGpsUpdateTime());
                }
                if (dto.isModified("lastSyncTime")) {
                    result.setLastSyncTime(dto.getLastSyncTime());
                }

                result.setStatus(dto.getStatus() != null ? dto.getStatus() : RadioStatus.ACTIVATED);
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
    public RadioDto toDto() {
        RadioDto result = super.toDto();
        RadioJpa jpa = getJpa();

        if (getSecurityLevel().gt(SecurityLevel.IDENTITY)) {
            if (getScope().gte(Scope.BASIC)) {
                result.setExternalId(jpa.getExternalId());
                result.setName(jpa.getName());
                result.setDescription(jpa.getDescription());
                result.setType(jpa.getType());
                result.setStatus(jpa.getStatus() != null ? jpa.getStatus() : RadioStatus.ACTIVATED);

                result.setConnectionStatus(jpa.getConnectionStatus());
                result.setBatteryLevel(jpa.getBatteryLevel());
                result.setDeviceState(jpa.getDeviceState());
                result.setRadioUserId(jpa.getRadioUserId());
                result.setGpsAltitude(jpa.getGpsAltitude());
                result.setGpsDirection(jpa.getGpsDirection());
                result.setGpsUpdateTime(jpa.getGpsUpdateTime());
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
