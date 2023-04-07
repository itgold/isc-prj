package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.SaltoConstants;
import lombok.Data;

import java.util.Date;

@Data
@JsonRootName("Period")
public class SaltoPeriodDto implements ISaltoDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SaltoConstants.FORMAT_DATE)
    @JsonProperty("StartDate")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SaltoConstants.FORMAT_DATE)
    @JsonProperty("EndDate")
    private Date endDate;
}
