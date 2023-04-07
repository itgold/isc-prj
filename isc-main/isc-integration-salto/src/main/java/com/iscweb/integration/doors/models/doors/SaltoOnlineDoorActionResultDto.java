package com.iscweb.integration.doors.models.doors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.integration.doors.models.ISaltoDto;
import lombok.Data;

@Data
public class SaltoOnlineDoorActionResultDto implements ISaltoDto {

    @JsonProperty("DoorID")
    private String id;

    @JsonProperty("ErrorCode")
    private int errorCode;

    @JsonProperty("ErrorMessage")
    private String errorMessage;
}
