package com.iscweb.integration.doors.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.integration.doors.models.ISaltoDto;
import lombok.Data;

@Data
public class SaltoDbDoorsRequestDto implements ISaltoDto {

    public static final int MAX_COUNT = 100;

    @JsonProperty("MaxCount")
    private Integer maxCount = SaltoDbDoorsRequestDto.MAX_COUNT;

    @JsonProperty("StartingFromExtDoorID")
    private String startingFromExtDoorId;

    @JsonProperty("ReturnMembership_Door_Zone")
    private Boolean returnMembershipDoorZone = Boolean.FALSE;

    @JsonProperty("ReturnMembership_Door_Location")
    private Boolean returnMembershipDoorLocation;

    @JsonProperty("ReturnMembership_Door_Function")
    private Boolean returnMembershipDoorFunction;
}
