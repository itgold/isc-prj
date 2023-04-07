package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.enums.SaltoErrorCode;
import lombok.Data;

@Data
@JsonRootName(SaltoErrorDto.NODE_NAME)
public class SaltoErrorDto implements ISaltoDto {

    public static final String NODE_NAME = "Exception";

    @JsonProperty("Code")
    private SaltoErrorCode code;

    @JsonProperty("Message")
    private String message;
}
