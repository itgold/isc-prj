package com.iscweb.integration.doors.models.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.ISaltoDto;
import lombok.Data;

@Data
@JsonRootName("SaltoDBGroup")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaltoDbGroupDto implements ISaltoDto {

    @JsonProperty("ExtGroupID")
    private String id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("SaltoDB.AccessPermissionList.Group_Door")
    private SaltoDbAccessPermissionListGroupDoorDto groupDoors;

    @JsonProperty("SaltoDB.AccessPermissionList.Group_Zone")
    private SaltoDbAccessPermissionListGroupZoneDto groupZones;

    @JsonProperty("SaltoDB.MembershipList.User_Group")
    private SaltoDbMembershipListUserGroupDto userGroups;
}
