package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.dto.entity.BaseExternalEntityDto;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.UtilityStatus;
import com.iscweb.common.model.metadata.UtilityType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

/**
 * Utility DTO object.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UtilityDto extends BaseExternalEntityDto {

    private UtilityType type;
    private UtilityStatus status;
    /**
     * @see IApplicationModel#getConverterType()
     */
    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.UTILITY;
    }

    /**
     * @see IExternalEntityDto#getEntityType()
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.UTILITY;
    }

    /**
     * @see IExternalEntityDto#getEntityId()
     */
    @Override
    public String getEntityId() {
        return getId();
    }
}
