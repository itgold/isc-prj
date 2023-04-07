package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.dto.BaseEntityDto;
import com.iscweb.common.model.metadata.ConverterType;
import org.springframework.data.annotation.Transient;

/**
 * Abstract DTO with basic user properties (only email and username).
 */
public abstract class BaseUserDto extends BaseEntityDto {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    /**
     * @see IApplicationModel#getConverterType()
     */
    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.USER;
    }
}
