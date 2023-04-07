package com.iscweb.integration.doors.models.users;

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
@JsonRootName("SaltoDBUserList")
public class SaltoDbUsersDto implements ISaltoDto {

    @JsonProperty("SaltoDBUser")
    private List<SaltoDbUserDto> users;

    @JsonProperty("EOF")
    private Boolean eof;

}
