package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName("SaltoDBCalendar")
public class SaltoDbCalendarDto implements ISaltoDto {

    @JsonProperty("CalendarID")
    private Integer calendarId;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Description")
    private String description;
}
