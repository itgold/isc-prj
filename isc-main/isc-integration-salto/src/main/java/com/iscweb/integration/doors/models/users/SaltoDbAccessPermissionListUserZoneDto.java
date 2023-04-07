package com.iscweb.integration.doors.models.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.ISaltoDto;
import lombok.Data;

import java.util.List;

@Data
@JsonRootName("SaltoDB.AccessPermissionList.User_Zone")
public class SaltoDbAccessPermissionListUserZoneDto implements ISaltoDto {

    @JsonProperty("SaltoDB.AccessPermission.User_Zone")
    private List<SaltoDbAccessPermissionUserZoneDto> permissions;
}
