package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName("AccessRight")
public class SaltoAccessRightDto implements ISaltoDto {

    @JsonProperty("AccessPoint")
    private SaltoAccessPointDto accessPoint;

    @JsonProperty("TimezoneList")
    private SaltoTimezoneListDto timezoneList;

    @JsonProperty("Period")
    private SaltoPeriodDto period;
}
