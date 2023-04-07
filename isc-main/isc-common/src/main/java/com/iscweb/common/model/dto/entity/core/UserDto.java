package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import java.time.ZonedDateTime;
import java.util.Set;

/**
 * User DTO object.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDto extends BaseUserDto {

    private UserStatus status;

    private String firstName;
    private String lastName;

    private ZonedDateTime lastLogin;
    private ZonedDateTime activationDate;

    private String password;

    private String imageUrl;

    private SchoolDistrictDto schoolDistrict;
    private Set<RoleDto> roles = Sets.newHashSet();

    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.USER;
    }
}
