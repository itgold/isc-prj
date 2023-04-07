package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.dto.entity.BaseExternalEntityDto;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.SafetyStatus;
import com.iscweb.common.model.metadata.SafetyType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

/**
 * Safety DTO object.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SafetyDto extends BaseExternalEntityDto {

    private SafetyType type;
    private SafetyStatus status;

    /**
     * @see IApplicationModel#getConverterType()
     */
    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.SAFETY;
    }

    /**
     * @see IExternalEntityDto#getEntityType()
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.SAFETY;
    }

    /**
     * @see IExternalEntityDto#getEntityId()
     */
    @Override
    public String getEntityId() {
        return getId();
    }
}
