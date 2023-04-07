package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName("SaltoDBTimedPeriodTable")
public class SaltoDbTimedPeriodTableDto implements ISaltoDto {

    @JsonProperty("TimedPeriodTableID")
    private Integer timedPeriodTableId;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("SaltoDBTimedPeriodList")
    private SaltoDbTimedPeriodListDto timePeriods;
}
