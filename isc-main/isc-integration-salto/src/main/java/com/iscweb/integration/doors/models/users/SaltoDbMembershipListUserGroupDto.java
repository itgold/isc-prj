package com.iscweb.integration.doors.models.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.ISaltoDto;
import lombok.Data;

import java.util.List;

@Data
@JsonRootName("SaltoDB.MembershipList.User_Group")
public class SaltoDbMembershipListUserGroupDto implements ISaltoDto {

    @JsonProperty("SaltoDB.Membership.User_Group")
    private List<SaltoDbMembershipUserGroupDto> permissions;
}
