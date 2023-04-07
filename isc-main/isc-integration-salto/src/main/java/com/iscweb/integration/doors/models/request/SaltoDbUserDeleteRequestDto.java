package com.iscweb.integration.doors.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.integration.doors.models.ISaltoDto;
import lombok.Data;

@Data
public class SaltoDbUserDeleteRequestDto implements ISaltoDto {

    @JsonProperty("ExtUserID")
    private String id;

}
