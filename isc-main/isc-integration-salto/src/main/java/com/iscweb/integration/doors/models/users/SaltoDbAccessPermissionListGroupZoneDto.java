package com.iscweb.integration.doors.models.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.ISaltoDto;
import lombok.Data;

import java.util.List;

@Data
@JsonRootName("SaltoDB.AccessPermissionList.Group_Zone")
public class SaltoDbAccessPermissionListGroupZoneDto implements ISaltoDto {

    @JsonProperty("SaltoDB.AccessPermission.Group_Zone")
    private List<SaltoDbAccessPermissionGroupZoneDto> permissions;
}
