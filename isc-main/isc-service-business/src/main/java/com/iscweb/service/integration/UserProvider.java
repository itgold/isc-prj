package com.iscweb.service.integration;

import com.iscweb.common.events.integration.IExternalEntityProvider;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.IDeviceStateDto;
import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.entity.core.ExternalUserDto;
import com.iscweb.common.model.entity.IExternalUser;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.user.ExternalUserEntityService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * External user provider implementation.
 */
@Component
public class UserProvider implements IExternalEntityProvider {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ExternalUserEntityService externalUserEntityService;

    @Override
    public IExternalEntityDto resolveEntityById(String deviceId) {
        return getExternalUserEntityService().findByGuid(deviceId, List.of(LazyLoadingField.DEVICE_STATE));
    }

    @Override
    public IExternalEntityDto resolveEntityByExternalId(String externalId) {
        return getExternalUserEntityService().findByExternalId(externalId, List.of(LazyLoadingField.DEVICE_STATE));
    }

    @Override
    public IExternalEntityDto update(IExternalEntityDto device, IExternalEntityDto updatedDevice, IDeviceStateDto updatedDeviceState) {
        IExternalEntityDto updated = updatedDevice != null ? updatedDevice : device;
        if (updatedDevice != null) {
            ExternalUserDto dto = (ExternalUserDto) updatedDevice;
            if (device == null) {
                updated = getExternalUserEntityService().create(dto);
            } else {
                updated = getExternalUserEntityService().update(dto);
            }
        }

        return updated;
    }
}
