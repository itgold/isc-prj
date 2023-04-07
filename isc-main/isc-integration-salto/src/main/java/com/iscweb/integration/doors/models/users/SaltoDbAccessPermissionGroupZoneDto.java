package com.iscweb.integration.doors.models.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.SaltoPeriodDto;
import lombok.Data;

@Data
@JsonRootName("SaltoDB.AccessPermission.Group_Zone")
public class SaltoDbAccessPermissionGroupZoneDto implements ISaltoDto {

    @JsonProperty("ExtGroupID")
    private String groupId;

    @JsonProperty("ExtZoneID")
    private String zoneId;

    @JsonProperty("TimezoneTableID")
    private Integer timezoneTableId;

    @JsonProperty("UsePeriod")
    private Boolean usePeriod;

    @JsonProperty("Period")
    private SaltoPeriodDto period;

}
