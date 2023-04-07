package com.iscweb.service.converter.impl;

import com.iscweb.common.model.dto.entity.core.RoleDto;
import com.iscweb.persistence.model.jpa.RoleJpa;
import com.iscweb.service.converter.BaseConverter;
import com.iscweb.service.converter.Convert;

public class RoleConverter extends BaseConverter<RoleDto, RoleJpa> {

    public RoleConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    @Override
    protected RoleJpa createJpa() {
        return new RoleJpa();
    }

    @Override
    protected RoleDto createDto() {
        return new RoleDto();
    }

    /**
     * @see super#toJpa()
     */
    @Override
    protected RoleJpa toJpa() {
        RoleJpa result = super.toJpa();
        RoleDto dto = getDto();

        result.setName(dto.getName());

        return result;
    }

    /**
     * @see super#toDto()
     */
    @Override
    protected RoleDto toDto() {
        RoleDto result = super.toDto();

        if (result != null) {
            RoleJpa jpa = getJpa();
            result.setName(jpa.getName());
        }

        return result;
    }
}
