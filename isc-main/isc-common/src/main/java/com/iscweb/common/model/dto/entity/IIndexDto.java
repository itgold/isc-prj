package com.iscweb.common.model.dto.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.iscweb.common.model.dto.IEntityDto;

/**
 * Contract for those DTO objects which are used to index different objects.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = IIndexDto.class)
public interface IIndexDto extends IEntityDto {

    @Override
    Long getRowId();

    @Override
    void setRowId(Long rowId);
}
