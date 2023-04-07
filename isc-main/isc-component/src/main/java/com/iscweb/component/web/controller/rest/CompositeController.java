package com.iscweb.component.web.controller.rest;

import com.iscweb.common.model.dto.DeviceActionAccumulator;
import com.iscweb.common.model.dto.DeviceActionResultDto;
import com.iscweb.common.model.dto.composite.IRegionComposite;
import com.iscweb.common.service.IDeviceActionHandler;
import com.iscweb.component.web.controller.BaseInternalApiController;
import com.iscweb.service.composite.CompositeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This is a rest controller for working with the region composite structures.
 */
@Slf4j
@RestController
@Api("REST controller for application regions composite")
public class CompositeController extends BaseInternalApiController<CompositeService> {

    @GetMapping(value = "/composite/{regionId}")
    @ResponseBody
    public IRegionComposite getComposite(@PathVariable(value = "regionId") String regionId) {
        return getService().build(regionId);
    }

    @GetMapping(value = "/composite/lockdown/{regionId}")
    @ResponseBody
    public DeviceActionResultDto lockDown(@PathVariable(value = "regionId") String regionId) {
        DeviceActionResultDto result;

        IRegionComposite composite = getService().build(regionId);
        if (composite != null) {
            result = composite.emergencyClose(DeviceActionAccumulator.build());
        } else {
            result = DeviceActionResultDto.builder()
                    .status(DeviceActionResultDto.ActionResult.FAILURE)
                    .errors(List.of(new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.UNKNOWN_DEVICE.name(),
                                                                                "Unable to resolve the door with id: " + regionId)))
                    .build();
        }

        return result;
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
