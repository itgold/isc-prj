package com.iscweb.common.model.dto;

import com.iscweb.common.model.EntityType;
import lombok.Data;

/**
 * Dto class to define device action to be executed.
 */
@Data
public class DeviceActionDto implements IDto {
    private EntityType entityType;
    private String deviceId;
    private String action;
}
