package com.iscweb.service.entity;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.BaseEntityDto;

public interface EntityService<DTO extends BaseEntityDto> {
    DTO create(DTO dto) throws ServiceException;
    DTO update(DTO dto) throws ServiceException;
    void delete(String guid);
}
