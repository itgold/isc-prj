package com.iscweb.service.converter.user;

import com.iscweb.common.model.dto.entity.core.RoleDto;
import com.iscweb.common.model.dto.entity.core.UserDto;
import com.iscweb.persistence.model.jpa.RoleJpa;
import com.iscweb.persistence.model.jpa.UserJpa;
import com.iscweb.persistence.repositories.impl.RoleJpaRepository;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Migrates users. Note that the user doesn't have a company attached, the wiring
 * must be done from the caller of the converter
 */
public class UserConverter extends BaseUserConverter<UserDto, UserJpa> {

    public UserConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    @Override
    protected UserJpa createJpa() {
        return new UserJpa();
    }

    @Override
    protected UserDto createDto() {
        return new UserDto();
    }

    @Override
    protected UserJpa toJpa() {
        UserJpa result = super.toJpa();
        UserDto dto = getDto();

        if (dto.getRowId() != null) {
            result.setId(dto.getRowId());
        }
        if (getScope() == Scope.ALL && !StringUtils.isEmpty(dto.getPassword())) {
            result.setPassword(dto.getPassword());
        }

        if (dto.isModified("lastLogin")) {
            result.setLastLogin(dto.getLastLogin());
        }
        if (dto.isModified("activationDate")) {
            result.setActivationDate(dto.getActivationDate());
        }

        if (dto.isModified("status")) {
            result.setStatus(dto.getStatus());
        }
        if (dto.isModified("firstName")) {
            result.setFirstName(dto.getFirstName());
        }
        if (dto.isModified("lastName")) {
            result.setLastName(dto.getLastName());
        }
        if (dto.isModified("lastLogin")) {
            result.setLastLogin(dto.getLastLogin());
        }
        if (dto.isModified("activationDate")) {
            result.setActivationDate(dto.getActivationDate());
        }
        if (dto.isModified("password")) {
            result.setPassword(dto.getPassword());
        }
        if (dto.isModified("imageUrl")) {
            result.setImageUrl(dto.getImageUrl());
        }

        Set<RoleDto> roles = dto.getRoles();
        if (!CollectionUtils.isEmpty(roles)) {
            result.setRoles(roles.stream()
                                 .filter(Objects::nonNull)
                                 .map(this::findRoleJpa)
                                 .collect(Collectors.toSet()));
        }

        return result;
    }

    @Override
    protected UserDto toDto() {
        UserDto result = super.toDto();
        UserJpa user = getJpa();

        result.setStatus(user.getStatus());
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        result.setImageUrl(user.getImageUrl());

        if (getScope() == Scope.ALL) {
            result.setPassword(user.getPassword());
        }

        result.setActivationDate(user.getActivationDate());
        result.setLastLogin(user.getLastLogin());
        result.setImageUrl(user.getImageUrl());
        result.setStatus(user.getStatus());

        if (!user.getRoles().isEmpty()) {
            result.setRoles(user.getRoles().stream()
                                .map(role -> RoleDto.valueOf(role.getName()))
                                .collect(Collectors.toSet()));
        }

        if (!CollectionUtils.isEmpty(user.getRoles())) {
            result.setRoles(user.getRoles().stream()
                                .map(role -> (RoleDto) Convert.my(role).boom())
                                .collect(Collectors.toSet()));
        }

        if (user.getSchoolDistrict() != null) {
            result.setSchoolDistrict(Convert.my(user.getSchoolDistrict()).scope(Scope.ALL).boom());
        }

        return result;
    }

    public RoleJpa findRoleJpa(RoleDto roleDto) {
        RoleJpaRepository roleRepository = getConvertContext().getRoleRepository();

        RoleJpa result;
        if (roleDto.getRowId() != null) {
            Optional<RoleJpa> roleById = roleRepository.findById(roleDto.getRowId());
            result = roleById.orElse(null);
        } else {
            result = roleRepository.findByName(roleDto.getName());
        }

        return result;
    }
}
