package com.iscweb.common.model.dto.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.iscweb.common.model.dto.BaseEntityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Abstract class for DTO objects which are used to index different objects.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = BaseIndexDto.class)
public abstract class BaseIndexDto extends BaseEntityDto implements IIndexDto {

    private String guid;

}
