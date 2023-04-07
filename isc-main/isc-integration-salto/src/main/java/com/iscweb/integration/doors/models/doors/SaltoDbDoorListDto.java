package com.iscweb.integration.doors.models.doors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.integration.doors.models.ISaltoDto;
import lombok.Data;

import java.util.List;

@Data
public class SaltoDbDoorListDto implements ISaltoDto {

    @JsonProperty("SaltoDBDoor")
    private List<SaltoDbDoorDto> doors;
}
