package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SaltoGetInfoDto implements ISaltoDto {

    @JsonProperty("ProtocolID")
    private String protocolId;

    @JsonProperty("ProtocolVersion")
    private String protocolVersion;

    @JsonProperty("DateTime")
    private String dateTime;

    @JsonProperty("DefaultLanguage")
    private String defaultLanguage;
}
