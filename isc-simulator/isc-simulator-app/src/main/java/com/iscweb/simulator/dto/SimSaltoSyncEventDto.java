package com.iscweb.simulator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.model.dto.IDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.integration.doors.models.enums.BatteryStatus;
import com.iscweb.integration.doors.models.enums.CommStatus;
import com.iscweb.integration.doors.models.enums.DoorOpeningMode;
import com.iscweb.integration.doors.models.enums.DoorStatus;
import com.iscweb.integration.doors.models.enums.TamperStatus;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.time.ZonedDateTime;

@Data
public class SimSaltoSyncEventDto implements IDto {

    private static final String INPUT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private String deviceId;
    private String deviceName;
    private String deviceDescription;
    private int batteryLevel;

    private DoorOpeningMode deviceOpeningMode;
    private DoorStatus doorStatus;
    private TamperStatus tamperStatus;
    private CommStatus commStatus;

    @Transient
    @JsonIgnore
    private DoorDto door;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = INPUT_DATE_PATTERN, timezone = "UTC")
    private ZonedDateTime eventDateTime = ZonedDateTime.now();

    @Transient
    @JsonIgnore
    public BatteryStatus getBatteryStatus() {
        if (this.batteryLevel >= 65) {
            return BatteryStatus.NORMAL;
        } else if (this.batteryLevel > 20 && this.batteryLevel < 65) {
            return BatteryStatus.LOW;
        } else {
            return BatteryStatus.VERY_LOW;
        }
    }

    public boolean isNameSet() {
        return null != this.getDeviceName() && !"".equals(this.getDeviceName());
    }

    public boolean isDescriptionSet() {
        return null != this.getDeviceDescription() && !"".equals(this.getDeviceDescription());
    }
}
