package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.SaltoConstants;
import lombok.Data;

import java.util.Date;

@Data
@JsonRootName("SaltoDBTimedPeriod")
public class SaltoDbTimedPeriodDto implements ISaltoDto {

    @JsonProperty("TimedPeriodID")
    private Integer timedPeriodId;

    @JsonProperty("TimedPeriodTableID")
    private Integer timedPeriodTableId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SaltoConstants.FORMAT_TIME)
    @JsonProperty("StartTime")
    private Date startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SaltoConstants.FORMAT_TIME)
    @JsonProperty("EndTime")
    private Date endTime;

    @JsonProperty("Monday")
    private Boolean monday;

    @JsonProperty("Tuesday")
    private Boolean tuesday;

    @JsonProperty("Wednesday")
    private Boolean wednesday;

    @JsonProperty("Thursday")
    private Boolean thursday;

    @JsonProperty("Friday")
    private Boolean friday;

    @JsonProperty("Saturday")
    private Boolean saturday;

    @JsonProperty("Sunday")
    private Boolean sunday;

    @JsonProperty("Holiday")
    private Boolean holiday;

    @JsonProperty("Special1")
    private Boolean special1;

    @JsonProperty("Special2")
    private Boolean special2;
}
