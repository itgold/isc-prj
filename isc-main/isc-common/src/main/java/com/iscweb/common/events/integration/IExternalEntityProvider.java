package com.iscweb.common.events.integration;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.IDeviceStateDto;

/**
 * Common interface for a specific device type.
 * Should be one provider implementation per device type.
 *
 * @see EntityType
 */
public interface IExternalEntityProvider {

    IExternalEntityDto resolveEntityById(String deviceId);

    IExternalEntityDto resolveEntityByExternalId(String externalId);

    default IDeviceStateDto resolveDeviceState(IExternalEntityDto device) { return null; };

    IExternalEntityDto update(IExternalEntityDto device, IExternalEntityDto updatedDevice, IDeviceStateDto updatedDeviceState) throws ServiceException;
}
