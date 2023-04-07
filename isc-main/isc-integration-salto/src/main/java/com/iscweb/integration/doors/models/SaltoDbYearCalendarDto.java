package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName("SaltoDBYearCalendar")
public class SaltoDbYearCalendarDto implements ISaltoDto {

    @JsonProperty("CalendarID")
    private Integer calendarId;

    @JsonProperty("Year")
    private Integer year;

    @JsonProperty("Month1")
    private String month1;

    @JsonProperty("Month2")
    private String month2;

    @JsonProperty("Month3")
    private String month3;

    @JsonProperty("Month4")
    private String month4;

    @JsonProperty("Month5")
    private String month5;

    @JsonProperty("Month6")
    private String month6;

    @JsonProperty("Month7")
    private String month7;

    @JsonProperty("Month8")
    private String month8;

    @JsonProperty("Month9")
    private String month9;

    @JsonProperty("Month10")
    private String month10;

    @JsonProperty("Month11")
    private String month11;

    @JsonProperty("Month12")
    private String month12;
}
