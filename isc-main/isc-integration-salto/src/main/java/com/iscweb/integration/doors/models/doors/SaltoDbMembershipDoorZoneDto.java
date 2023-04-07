package com.iscweb.integration.doors.models.doors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.SaltoPeriodDto;
import lombok.Data;

@Data
@JsonRootName("SaltoDB.Membership.Door_Zone")
public class SaltoDbMembershipDoorZoneDto implements ISaltoDto {

    @JsonProperty("ExtUserID")
    private String extUserId;

    @JsonProperty("ExtZoneID")
    private String extZoneId;

    @JsonProperty("TimezoneTableID")
    private Integer timezoneTableId;

    @JsonProperty("UsePeriod")
    private Boolean usePeriod;

    @JsonProperty("Period")
    private SaltoPeriodDto period;

}
