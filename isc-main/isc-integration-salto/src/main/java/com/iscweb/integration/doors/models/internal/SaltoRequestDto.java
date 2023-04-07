package com.iscweb.integration.doors.models.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.common.model.dto.IDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("RequestCall")
public class SaltoRequestDto implements IDto {

    @JsonProperty("RequestName")
    private String requestName;

    @JsonProperty("Params")
    private Object params;
}
