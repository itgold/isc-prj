package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.SaltoConstants;
import lombok.Data;

import java.util.Date;

@Data
@JsonRootName("Timezone")
public class SaltoTimezoneDto implements ISaltoDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SaltoConstants.FORMAT_TIME)
    @JsonProperty("StartTime")
    private Date startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SaltoConstants.FORMAT_TIME)
    @JsonProperty("EndTime")
    private Date endTime;

    @JsonProperty("DayType")
    private String dayType;
}
