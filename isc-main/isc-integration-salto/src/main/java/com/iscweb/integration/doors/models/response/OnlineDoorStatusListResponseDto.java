package com.iscweb.integration.doors.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.doors.OnlineDoorStatusListDto;
import lombok.Data;

@Data
public class OnlineDoorStatusListResponseDto implements ISaltoDto {

    @JsonProperty("OnlineDoorStatusList")
    private OnlineDoorStatusListDto doorsList;

}
