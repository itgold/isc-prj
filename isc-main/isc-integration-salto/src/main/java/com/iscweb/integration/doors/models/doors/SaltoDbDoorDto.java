package com.iscweb.integration.doors.models.doors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.enums.DoorOpeningMode;
import lombok.Data;

import java.util.List;

@Data
@JsonRootName("SaltoDBDoor")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaltoDbDoorDto implements ISaltoDto {

    @JsonProperty("ExtDoorID")
    private String id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("GPF1")
    private String gpf1;

    @JsonProperty("GPF2")
    private String gpf2;

    @JsonProperty("OpenTime")
    private int openTime;

    @JsonProperty("OpenTimeADA")
    private int openTimeAda;

    @JsonProperty("OpeningMode")
    private DoorOpeningMode openingMode;

    @JsonProperty("TimedPeriodsTableID")
    private int timedPeriodsTableId;

    @JsonProperty("AutomaticChangesTableID")
    private int automaticChangesTableId;

    @JsonProperty("KeypadCode")
    private byte keypadCode;

    @JsonProperty("AuditOnKeys")
    private Boolean auditOnKeys;

    @JsonProperty("AntipassbackEnabled")
    private Boolean antipassbackEnabled;

    @JsonProperty("OutwardAntipassback")
    private Boolean outwardAntipassback;

    @JsonProperty("UpdateRequired")
    private Boolean updateRequired;

    @JsonProperty("BatteryStatus")
    private int batteryStatus;

    @JsonProperty("SaltoDB.MembershipList.Door_Zone")
    private List<SaltoDbMembershipListDoorZoneDto> doorZones;

    // List<SaltoDB.MembershipList.Door_Location> doorLocations;
    // List<SaltoDB.MembershipList.Door_Function> doorFunctions;
}
