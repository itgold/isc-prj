package com.iscweb.integration.doors.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.integration.doors.models.ISaltoDto;
import lombok.Data;

@Data
public class SaltoDbUsersRequestDto implements ISaltoDto {

    public static final int MAX_COUNT = 100;

    @JsonProperty("MaxCount")
    private Integer maxCount = SaltoDbUsersRequestDto.MAX_COUNT;

    @JsonProperty("StartingFromExtUserID")
    private String startingFromExtUserId;

    @JsonProperty("ReturnMembership_User_Group")
    private Boolean returnGroupPermissions = Boolean.FALSE;

    @JsonProperty("ReturnAccessPermissions_User_Door")
    private Boolean returnDoorPermissions = Boolean.FALSE;

    @JsonProperty("ReturnAccessPermissions_User_Zone")
    private Boolean returnZonePermissions = Boolean.FALSE;

    @JsonProperty("ReturnAccessPermissions_User_Location")
    private Boolean returnLocationPermissions = Boolean.FALSE;

    @JsonProperty("ReturnAccessPermissions_User_Function")
    private Boolean returnFunctionPermissions = Boolean.FALSE;

    @JsonProperty("ReturnAccessPermissions_User_Output")
    private Boolean returnOutputPermissions = Boolean.FALSE;

    @JsonProperty("ReturnAccessPermissions_User_RoomReservation")
    private Boolean returnRoomPermissions = Boolean.FALSE;

}
