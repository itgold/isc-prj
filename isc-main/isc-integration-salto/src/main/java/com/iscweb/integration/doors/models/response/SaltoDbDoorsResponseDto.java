package com.iscweb.integration.doors.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.doors.SaltoDbDoorListDto;
import lombok.Data;

@Data
public class SaltoDbDoorsResponseDto implements ISaltoDto {

    @JsonProperty("SaltoDBDoorList")
    private SaltoDbDoorListDto doorsList;

    @JsonProperty("EOF")
    private Boolean eof;

}
