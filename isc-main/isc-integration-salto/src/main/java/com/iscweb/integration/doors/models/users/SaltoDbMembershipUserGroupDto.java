package com.iscweb.integration.doors.models.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.SaltoPeriodDto;
import lombok.Data;

@Data
@JsonRootName("SaltoDB.Membership.User_Group")
public class SaltoDbMembershipUserGroupDto implements ISaltoDto {

    @JsonProperty("ExtUserID")
    private String userId;

    @JsonProperty("ExtGroupID")
    private String groupId;

    @JsonProperty("UsePeriod")
    private Boolean usePeriod;

    @JsonProperty("Period")
    private SaltoPeriodDto period;

}
