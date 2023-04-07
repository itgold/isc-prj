package com.iscweb.integration.radios.models;

import com.iscweb.common.model.dto.IDto;
import lombok.Data;

/**
 * Radio device hardware Alarm information Dto.
 */
@Data
public class DeviceAlarmDto implements IDto {
    private DeviceGPSInfoDto gpsInfo;
    private String alarmText;
    private String deviceStatusName;
}
