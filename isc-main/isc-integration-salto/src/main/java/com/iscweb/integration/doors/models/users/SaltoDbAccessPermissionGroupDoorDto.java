package com.iscweb.integration.doors.models.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.SaltoPeriodDto;
import lombok.Data;

@Data
@JsonRootName("SaltoDB.AccessPermission.Group_Door")
public class SaltoDbAccessPermissionGroupDoorDto implements ISaltoDto {

    @JsonProperty("ExtGroupID")
    private String groupId;

    @JsonProperty("ExtDoorID")
    private String zoneId;

    @JsonProperty("UsePeriod")
    private Boolean usePeriod;

    @JsonProperty("Period")
    private SaltoPeriodDto period;

}
