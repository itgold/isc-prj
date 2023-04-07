package com.iscweb.integration.doors.models.doors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.enums.BatteryStatus;
import com.iscweb.integration.doors.models.enums.CommStatus;
import com.iscweb.integration.doors.models.enums.DoorStatus;
import com.iscweb.integration.doors.models.enums.TamperStatus;
import lombok.Data;

@Data
@JsonRootName("OnlineIPDoorStatus")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OnlineIpDoorStatusDto implements ISaltoDto {

    @JsonProperty("DoorID")
    private String doorId;

    @JsonProperty("CommStatus")
    private CommStatus commStatus;

    @JsonProperty("DoorStatus")
    private DoorStatus doorStatus;

    @JsonProperty("BatteryStatus")
    private BatteryStatus batteryStatus;

    @JsonProperty("TamperStatus")
    private TamperStatus tamperStatus;
}
