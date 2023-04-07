package com.iscweb.service.converter.impl;

import com.iscweb.common.model.dto.entity.core.TagDto;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.service.converter.BaseConverter;
import com.iscweb.service.converter.Convert;

public class TagConverter extends BaseConverter<TagDto, TagJpa> {

    public TagConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    @Override
    protected TagJpa createJpa() {
        return new TagJpa();
    }

    @Override
    protected TagDto createDto() {
        return new TagDto();
    }

    /**
     * @see super#toJpa()
     */
    @Override
    protected TagJpa toJpa() {
        TagDto dto = getDto();
        TagJpa result = super.toJpa();

        result.setName(dto.getName());

        return result;
    }

    /**
     * @see super#toDto()
     */
    @Override
    protected TagDto toDto() {
        TagJpa jpa = getJpa();
        TagDto result = super.toDto();

        result.setName(jpa.getName());

        return result;
    }
}
