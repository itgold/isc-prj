package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("SaltoDBTimezoneList")
public class SaltoDbTimezoneListDto implements ISaltoDto {

    @JsonProperty("SaltoDBTimezone")
    private List<SaltoDbTimezoneDto> timezones;
}
