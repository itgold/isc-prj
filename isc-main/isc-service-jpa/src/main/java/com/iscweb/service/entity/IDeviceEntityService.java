package com.iscweb.service.entity;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.BaseEntityDto;
import com.iscweb.common.model.dto.entity.core.TagDto;

import java.util.List;

public interface IDeviceEntityService<DTO extends BaseEntityDto> {
    DTO create(DTO dto, List<TagDto> tags) throws ServiceException;
    DTO update(DTO dto, List<TagDto> tags) throws ServiceException;
    void delete(String guid);
}
