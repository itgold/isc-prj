package com.iscweb.integration.doors.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.doors.SaltoDoorNameListDto;
import com.iscweb.integration.doors.models.doors.SaltoExtDoorIdListDto;
import lombok.Data;

@Data
public class SaltoEmergencyOpenRequestDto implements ISaltoDto {

    @JsonProperty("DoorNameList")
    private SaltoDoorNameListDto doorNames;

    @JsonProperty("ExtDoorIDList")
    private SaltoExtDoorIdListDto doorIds;
}
