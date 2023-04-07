package com.iscweb.integration.doors.models.doors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.ISaltoDto;
import lombok.Data;

import java.util.List;

@Data
@JsonRootName("SaltoDB.MembershipList.Door_Zone")
public class SaltoDbMembershipListDoorZoneDto implements ISaltoDto {

    @JsonProperty("SaltoDB.Membership.Door_Zone")
    private List<SaltoDbMembershipDoorZoneDto> permissions;
}
