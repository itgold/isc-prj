package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.BaseExternalEntityDto;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.SpeakerStatus;
import com.iscweb.common.model.metadata.SpeakerType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import java.util.Set;

/**
 * Speaker DTO object.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SpeakerDto extends BaseExternalEntityDto {

    private SpeakerStatus status;
    private SpeakerType type;
    private Set<DeviceStateItemDto> state;

    /**
     * @see IApplicationModel#getConverterType()
     */
    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.SPEAKER;
    }

    /**
     * @see IExternalEntityDto#getEntityType()
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.SPEAKER;
    }

    /**
     * @see IExternalEntityDto#getEntityId()
     */
    @Override
    public String getEntityId() {
        return getId();
    }
}
