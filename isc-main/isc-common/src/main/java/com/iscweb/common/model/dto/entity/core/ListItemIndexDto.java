package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.dto.entity.BaseIndexDto;
import com.iscweb.common.model.metadata.ConverterType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

/**
 * DTO object which indexes an item in a list.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListItemIndexDto extends BaseIndexDto {

    /**
     * @see IApplicationModel#getConverterType()
     */
    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.INDEX;
    }
}
