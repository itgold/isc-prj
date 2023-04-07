package com.iscweb.integration.doors.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.doors.SaltoOnlineDoorActionResultsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaltoOnlineDoorActionResponseDto implements ISaltoDto {

    @JsonProperty("OnlineDoorActionResultList")
    private SaltoOnlineDoorActionResultsDto resultsList;
}
