package com.iscweb.service.actions;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.IApplicationComponent;
import com.iscweb.common.model.dto.DeviceActionAccumulator;
import com.iscweb.common.model.dto.IEntityDto;
import com.iscweb.common.model.dto.entity.BaseExternalEntityDto;
import com.iscweb.service.BaseDeviceBusinessService;
import com.iscweb.service.RegionService;
import com.iscweb.service.composite.leaf.DoorComposite;
import com.iscweb.service.composite.leaf.RegionComposite;
import com.iscweb.service.entity.IDeviceActionService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service implements different device actions.
 * This service is facade service to be used in composite nodes to execute different device actions.
 *
 * @see DoorComposite#emergencyClose(DeviceActionAccumulator accumulator)
 * @see RegionComposite#emergencyClose(DeviceActionAccumulator accumulator)
 */
@Slf4j
public abstract class BaseDeviceActionService<DTO extends BaseExternalEntityDto, S extends BaseDeviceBusinessService<DTO>> implements IApplicationComponent, IDeviceActionService {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RegionService regionService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private S service;

    public boolean save(IEntityDto dto) throws ServiceException {
        getService().update((DTO) dto, null);
        return true;
    }

    @Override
    public void deleteParent(String regionId) throws ServiceException {
        getRegionService().delete(regionId);
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
