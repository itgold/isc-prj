package com.iscweb.service.converter.user;

import com.iscweb.common.model.dto.entity.core.BaseUserDto;
import com.iscweb.persistence.model.jpa.UserJpa;
import com.iscweb.service.converter.BaseConverter;
import com.iscweb.service.converter.Convert;

/**
 * A converter which copies only the minimum data (name and email).
 *
 * @param <D> A user DTO implementation.
 * @param <J> A user JPA implementation.
 */
public abstract class BaseUserConverter<D extends BaseUserDto, J extends UserJpa> extends BaseConverter<D, J> {

    public BaseUserConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    /**
     * @see super#toJpa()
     */
    @Override
    protected J toJpa() {
        J result = super.toJpa();
        D dto = getDto();

        if (dto.isModified("email")) {
            result.setEmail(dto.getEmail());
        }

        return result;
    }

    /**
     * @see super#toDto()
     */
    @Override
    protected D toDto() {
        J jpa = getJpa();
        D result = super.toDto();

        if (jpa.getEmail() != null) {
            result.setEmail(jpa.getEmail());
        }

        return result;
    }
}
