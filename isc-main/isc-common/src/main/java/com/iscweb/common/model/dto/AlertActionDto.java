package com.iscweb.common.model.dto;

import com.iscweb.common.model.metadata.AlertSeverity;
import com.iscweb.common.model.metadata.AlertStatus;
import lombok.Data;

/**
 * Dto class to define device action to be executed.
 */
@Data
public class AlertActionDto implements IDto {
    private String alertId;
    private AlertStatus alertStatus;
    private AlertSeverity alertSeverity;
    private String notes;
}
