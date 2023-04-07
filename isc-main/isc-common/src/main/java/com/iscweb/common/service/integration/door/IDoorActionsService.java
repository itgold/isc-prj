package com.iscweb.common.service.integration.door;

import com.iscweb.common.model.dto.DeviceActionResultDto;
import com.iscweb.common.service.IApplicationService;

import java.util.Set;

/**
 * Abstract Door service interface.
 *
 * This interface should be implemented by all third party integration
 * modules which are providing door integration.
 */
public interface IDoorActionsService extends IApplicationService {

    String serviceInfo();

    /**
     * Start Emergency Open mode for a door.
     * @param deviceIds set of device ids to start emergency open scenario.
     */
    DeviceActionResultDto emergencyOpen(Set<String> deviceIds);

    /**
     * Start Emergency Close mode a door.
     * @param deviceIds set of devices to close.
     */
    DeviceActionResultDto emergencyClose(Set<String> deviceIds);

    /**
     * Cancel Emergency mode for a door.
     * @param deviceIds set of devices to finish emergency.
     */
    DeviceActionResultDto endEmergency(Set<String> deviceIds);

    /**
     * Open the door.
     * @param deviceIds set of devices to open the door.
     */
    DeviceActionResultDto openDoor(Set<String> deviceIds);
}
