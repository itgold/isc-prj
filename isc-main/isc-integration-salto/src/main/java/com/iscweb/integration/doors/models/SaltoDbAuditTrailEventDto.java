package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.iscweb.integration.doors.SaltoConstants;
import com.iscweb.integration.doors.models.enums.Operation;
import lombok.Data;

import java.util.Date;

@Data
@JsonRootName("SaltoDBAuditTrailEvent")
public class SaltoDbAuditTrailEventDto implements ISaltoDto {

    @JsonProperty("EventID")
    private Integer eventId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SaltoConstants.FORMAT_DATE_TIME)
    @JsonProperty("EventDateTime")
    private Date eventDateTime;

    @JsonProperty("Operation")
    private Operation operation;

    @JsonProperty("IsExit")
    private Boolean isExit;

    @JsonProperty("DoorID")
    private String doorId;

    @JsonProperty("SubjectType")
    private Byte subjectType;

    @JsonProperty("SubjectID")
    private String subjectId;
}
