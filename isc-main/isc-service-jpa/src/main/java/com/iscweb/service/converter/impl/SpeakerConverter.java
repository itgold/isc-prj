package com.iscweb.service.converter.impl;

import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.SpeakerDto;
import com.iscweb.common.model.metadata.SpeakerStatus;
import com.iscweb.common.model.metadata.SpeakerType;
import com.iscweb.persistence.model.jpa.DeviceStateItemJpa;
import com.iscweb.persistence.model.jpa.SpeakerJpa;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.converter.SecurityLevel;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

public class SpeakerConverter extends BaseRegionEntityConverter<SpeakerDto, SpeakerJpa> {

    public SpeakerConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    protected SpeakerJpa createJpa() {
        return new SpeakerJpa();
    }

    protected SpeakerDto createDto() {
        return new SpeakerDto();
    }

    /**
     * @see super#toJpa()
     */
    @Override
    protected SpeakerJpa toJpa() {
        SpeakerJpa result = super.toJpa();
        SpeakerDto dto = getDto();

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

                result.setType(dto.getType() != null ? dto.getType() : SpeakerType.UNKNOWN);
                result.setStatus(dto.getStatus() != null ? dto.getStatus() : SpeakerStatus.ACTIVATED);
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
    public SpeakerDto toDto() {
        SpeakerDto result = super.toDto();
        SpeakerJpa jpa = getJpa();

        if (getSecurityLevel().gt(SecurityLevel.IDENTITY)) {
            if (getScope().gte(Scope.BASIC)) {
                result.setExternalId(jpa.getExternalId());
                result.setType(jpa.getType());
                result.setStatus(jpa.getStatus() != null ? jpa.getStatus() : SpeakerStatus.ACTIVATED);
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
