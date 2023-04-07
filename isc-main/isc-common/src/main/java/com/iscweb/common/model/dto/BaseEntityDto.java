package com.iscweb.common.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.model.metadata.ConverterType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import java.time.ZonedDateTime;

/**
 * A base class for the entity DTO objects.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseEntityDto extends BaseDto implements IEntityDto, ITrackableDto {

    private String id;
    private Long rowId;
    private ZonedDateTime created;
    private ZonedDateTime updated;

    /**
     * @see com.iscweb.common.model.IApplicationModel#getConverterType()
     */
    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.UNSUPPORTED;
    }
}
