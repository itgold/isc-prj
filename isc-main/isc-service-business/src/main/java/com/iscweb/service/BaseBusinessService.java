package com.iscweb.service;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.BaseEntityDto;
import com.iscweb.common.service.IApplicationSecuredService;
import com.iscweb.service.entity.EntityService;
import org.springframework.transaction.annotation.Transactional;

@Transactional(transactionManager = "jpaTransactionManager")
public abstract class BaseBusinessService<DTO extends BaseEntityDto> implements IApplicationSecuredService {

    public abstract EntityService<DTO> getEntityService();

    public DTO create(DTO dto) throws ServiceException {
        return getEntityService().create(dto);
    }

    public DTO update(DTO dto) throws ServiceException {
        return getEntityService().update(dto);
    }

    public void delete(String guid) {
        getEntityService().delete(guid);
    }
}
