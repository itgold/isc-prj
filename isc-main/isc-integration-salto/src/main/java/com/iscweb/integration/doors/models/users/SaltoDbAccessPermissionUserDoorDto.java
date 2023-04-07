package com.iscweb.integration.doors.models.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.SaltoPeriodDto;
import lombok.Data;

@Data
@JsonRootName("SaltoDB.AccessPermission.User_Door")
public class SaltoDbAccessPermissionUserDoorDto implements ISaltoDto {

    @JsonProperty("ExtUserID")
    private String extUserId;

    @JsonProperty("ExtDoorID")
    private String extDoorId;

    @JsonProperty("TimezoneTableID")
    private Integer timezoneTableId;

    @JsonProperty("UsePeriod")
    private Boolean usePeriod;

    @JsonProperty("Period")
    private SaltoPeriodDto period;
}
