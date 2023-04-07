package com.iscweb.integration.doors.models.doors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.models.ISaltoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("OnlineDoorActionResultList")
public class SaltoOnlineDoorActionResultsDto implements ISaltoDto {

    @JsonProperty("OnlineDoorActionResult")
    private List<SaltoOnlineDoorActionResultDto> results;
}
