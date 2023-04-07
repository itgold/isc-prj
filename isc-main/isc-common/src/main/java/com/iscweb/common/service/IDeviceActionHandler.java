package com.iscweb.common.service;

import com.iscweb.common.model.IApplicationComponent;
import com.iscweb.common.model.dto.DeviceActionDto;
import com.iscweb.common.model.dto.DeviceActionResultDto;
import org.springframework.security.access.prepost.PreAuthorize;

import static com.iscweb.common.security.ApplicationSecurity.MAP_ACTION_PERMISSION;

/**
 * Interface to be implemented by device action handler.
 */
@PreAuthorize(MAP_ACTION_PERMISSION)
public interface IDeviceActionHandler extends IApplicationComponent {

    /**
     * Common Device action failure codes.
     */
    enum ActionError {
        NOT_IMPLEMENTED,
        UNKNOWN_DEVICE,
        ACTION_FAILED
    }

    /**
     * Action code handled by this device action handler.
     * Important: Action code must be unique!
     *
     * @return Action code
     */
    String actionCode();

    /**
     * Execute action.
     *
     * @param action Action details
     * @return Result of the action execution.
     */
    DeviceActionResultDto execute(DeviceActionDto action);
}
