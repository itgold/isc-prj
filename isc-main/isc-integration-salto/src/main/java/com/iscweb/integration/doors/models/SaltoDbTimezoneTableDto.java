package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName("SaltoDBTimezoneTable")
public class SaltoDbTimezoneTableDto implements ISaltoDto {

    @JsonProperty("TimezoneTableID")
    private Integer timezoneTableId;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("SaltoDBTimezoneList")
    private SaltoDbTimezoneListDto timezones;
}
