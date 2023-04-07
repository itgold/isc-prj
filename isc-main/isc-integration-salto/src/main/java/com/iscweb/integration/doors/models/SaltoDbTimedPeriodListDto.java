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
@JsonRootName("SaltoDBTimedPeriodList")
public class SaltoDbTimedPeriodListDto implements ISaltoDto {

    @JsonProperty("SaltoDBTimedPeriod")
    private List<SaltoDbTimedPeriodDto> timePeriods;
}
