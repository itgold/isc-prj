package com.iscweb.service.integration.door.actions;

import com.iscweb.common.model.dto.DeviceActionAccumulator;
import com.iscweb.common.model.dto.DeviceActionDto;
import com.iscweb.common.model.dto.DeviceActionResultDto;
import com.iscweb.common.model.dto.composite.IRegionComposite;
import com.iscweb.common.service.IDeviceActionHandler;
import com.iscweb.service.composite.CompositeService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Component
public class EmergencyCloseActionHandler implements IDeviceActionHandler {

    private static final String ACTION = "emergencyClose";

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CompositeService compositeService;

    @Override
    public String actionCode() {
        return ACTION;
    }

    @Override
    public DeviceActionResultDto execute(DeviceActionDto action) {
        DeviceActionResultDto result;

        IRegionComposite composite = getCompositeService().build(action.getDeviceId());
        if (composite != null) {
            result = composite.emergencyClose(DeviceActionAccumulator.build());
        } else {
            result = DeviceActionResultDto.builder()
                    .status(DeviceActionResultDto.ActionResult.FAILURE)
                    .errors(List.of(new DeviceActionResultDto.DeviceActionError(ActionError.UNKNOWN_DEVICE.name(),
                            "Unable to resolve the door with id: " + action.getDeviceId())))
                    .build();
        }

        return result;
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
