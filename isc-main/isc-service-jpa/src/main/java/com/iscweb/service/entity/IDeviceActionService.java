package com.iscweb.service.entity;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.IEntityDto;
import com.iscweb.common.service.IApplicationSecuredService;

/**
 * Base interface to define contract for the service implementing different device actions.
 *
 * Important: Implementation of this service is moved to the business service layer to make sure that any modifications
 * for the devices or composite hierarchy will be properly listened and intersected with a caching handler.
 */
public interface IDeviceActionService extends IApplicationSecuredService {

    boolean save(IEntityDto entityDto) throws ServiceException;

    void deleteParent(String regionId) throws ServiceException;
}
