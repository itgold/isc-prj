package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.dto.BaseEntityDto;
import com.iscweb.common.model.metadata.ConverterType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

/**
 * Tag DTO object.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = TagDto.class)
public class TagDto extends BaseEntityDto {

    private String name;

    /**
     * A factory method.
     * @param name role name.
     * @return role dto instance.
     */
    public static TagDto valueOf(String name) {
        TagDto result = new TagDto();
        result.setName(name);
        return result;
    }

    /**
     * @see IApplicationModel#getConverterType()
     */
    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.TAG;
    }
}
