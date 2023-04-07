package com.iscweb.service.integration;

import com.iscweb.common.model.dto.DeviceActionResultDto;
import com.iscweb.common.service.IDeviceActionHandler;
import com.iscweb.common.service.integration.door.IDoorActionsService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

/**
 * Default door service implementation that is used when door provider integration is not enabled.
 * Does nothing by default, returns an error code.
 */
@Slf4j
public class DefaultDoorActionsService implements IDoorActionsService {

    @Override
    public String serviceInfo() {
        return "NO DOOR SERVICE AVAILABLE";
    }

    /**
     * @see IDoorActionsService#emergencyOpen(Set)
     */
    @Override
    public DeviceActionResultDto emergencyOpen(Set<String> deviceIds) {
        return DeviceActionResultDto.builder()
                                    .status(DeviceActionResultDto.ActionResult.FAILURE)
                                    .errors(List.of(new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.NOT_IMPLEMENTED.name(),
                                                                                                "Emergency Open action is not enabled!")))
                                    .build();
    }

    /**
     * @see IDoorActionsService#emergencyClose(Set)
     */
    @Override
    public DeviceActionResultDto emergencyClose(Set<String> deviceIds) {
        return DeviceActionResultDto.builder()
                .status(DeviceActionResultDto.ActionResult.FAILURE)
                .errors(List.of(new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.NOT_IMPLEMENTED.name(),
                        "Emergency Close action is not enabled!")))
                .build();
    }

    /**
     * @see IDoorActionsService#endEmergency(Set)
     */
    @Override
    public DeviceActionResultDto endEmergency(Set<String> deviceIds) {
        return DeviceActionResultDto.builder()
                .status(DeviceActionResultDto.ActionResult.FAILURE)
                .errors(List.of(new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.NOT_IMPLEMENTED.name(),
                        "End Emergency action is not enabled!")))
                .build();
    }

    /**
     * @see IDoorActionsService#openDoor(Set)
     */
    @Override
    public DeviceActionResultDto openDoor(Set<String> deviceIds) {
        return DeviceActionResultDto.builder()
                .status(DeviceActionResultDto.ActionResult.FAILURE)
                .errors(List.of(new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.NOT_IMPLEMENTED.name(),
                        "Open Door action is not enabled!")))
                .build();
    }
}
