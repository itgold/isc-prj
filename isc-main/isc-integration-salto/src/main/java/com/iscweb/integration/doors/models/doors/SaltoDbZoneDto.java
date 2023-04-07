package com.iscweb.integration.doors.models.doors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.ISaltoDto;
import lombok.Data;

@Data
@JsonRootName("SaltoDBZone")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaltoDbZoneDto implements ISaltoDto {

    @JsonProperty("ExtZoneID")
    private String id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("IsLow")
    private boolean isLow = Boolean.FALSE;

    @JsonProperty("IsFreeAssignment")
    private boolean isFreeAssignment = Boolean.FALSE;

    @JsonProperty("FreeAssignmentGroup")
    private int freeAssignmentGroup = 1;

    @JsonProperty("SaltoDB.MembershipList.Door_Zone")
    private SaltoDbMembershipListDoorZoneDto doorZones;
}
