package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.enums.AccessType;
import lombok.Data;

@Data
@JsonRootName("AccessPoint")
public class SaltoAccessPointDto implements ISaltoDto {

    @JsonProperty("AccessType")
    private AccessType accessType;

    @JsonProperty("AccessID")
    private String accessId;
}
