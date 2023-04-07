package com.iscweb.service.converter.impl;

import com.iscweb.common.model.dto.entity.IIndexDto;
import com.iscweb.common.model.dto.entity.core.ListItemIndexDto;
import com.iscweb.persistence.model.jpa.IndexJpa;
import com.iscweb.service.converter.BaseConverter;
import com.iscweb.service.converter.Convert;

public class IndexConverter<D extends IIndexDto> extends BaseConverter<D, IndexJpa> {

    public IndexConverter(Convert.ConvertContext convertContext) {
        super(convertContext);
    }

    @Override
    protected IndexJpa createJpa() {
        return new IndexJpa();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected D createDto() {
        return (D) new ListItemIndexDto();
    }
}
