package com.iscweb.integration.doors.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.doors.OnlineIpDoorStatusListDto;
import lombok.Data;

@Data
public class OnlineIpDoorStatusListResponseDto implements ISaltoDto {

    @JsonProperty("OnlineIPDoorStatusList")
    private OnlineIpDoorStatusListDto doorsList;

}
