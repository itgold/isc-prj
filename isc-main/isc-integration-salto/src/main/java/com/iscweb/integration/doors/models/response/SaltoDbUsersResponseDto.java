package com.iscweb.integration.doors.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.integration.doors.models.ISaltoDto;
import com.iscweb.integration.doors.models.users.SaltoDbUsersDto;
import lombok.Data;

@Data
public class SaltoDbUsersResponseDto implements ISaltoDto {

    @JsonProperty("SaltoDBUserList")
    private SaltoDbUsersDto usersList;

    @JsonProperty("EOF")
    private Boolean eof;

}
