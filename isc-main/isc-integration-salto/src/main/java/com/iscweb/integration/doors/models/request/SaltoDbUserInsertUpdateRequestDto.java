package com.iscweb.integration.doors.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.users.SaltoDbUserDto;
import lombok.Data;

@Data
public class SaltoDbUserInsertUpdateRequestDto implements ISaltoDto {

    @JsonProperty("SaltoDBUser")
    private SaltoDbUserDto user;

}
