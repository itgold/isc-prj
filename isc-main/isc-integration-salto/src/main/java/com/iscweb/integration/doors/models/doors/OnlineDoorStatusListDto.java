package com.iscweb.integration.doors.models.doors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.ISaltoDto;
import lombok.Data;

import java.util.List;

@Data
@JsonRootName("OnlineDoorStatusList")
public class OnlineDoorStatusListDto implements ISaltoDto {

    @JsonProperty("OnlineDoorStatus")
    private List<OnlineDoorStatusDto> doors;
}
