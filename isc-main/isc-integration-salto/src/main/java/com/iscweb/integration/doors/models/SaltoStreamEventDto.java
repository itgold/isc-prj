package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.integration.doors.SaltoConstants;
import com.iscweb.integration.doors.events.SaltoEventTypes;
import com.iscweb.integration.doors.models.enums.Operation;
import com.iscweb.integration.doors.models.enums.UserType;
import lombok.Data;

import java.util.Date;

import static com.iscweb.integration.doors.SaltoConstants.FORMAT_DATE_TIME;

@Data
public class SaltoStreamEventDto implements ISaltoDto, ITypedPayload {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMAT_DATE_TIME)
    @JsonProperty("EventDateTime")
    private Date eventDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMAT_DATE_TIME)
    @JsonProperty("EventDateTimeUTC")
    private Date eventDateTimeUtc;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SaltoConstants.FORMAT_TIME)
    @JsonProperty("EventTime")
    private Date eventTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = SaltoConstants.FORMAT_TIME)
    @JsonProperty("EventTimeUTC")
    private Date eventTimeUtc;

    @JsonProperty("OperationID")
    private Operation operation;

    @JsonProperty("OperationDescription")
    private String description;

    @JsonProperty("IsExit")
    private Boolean isExit;

    @JsonProperty("UserType")
    private UserType userType;

    @JsonProperty("UserName")
    private String userName;

    @JsonProperty("UserExtID")
    private String userExtId;

    @JsonProperty("UserGPF1")
    private String userGpf1;

    @JsonProperty("UserGPF2")
    private String userGpf2;

    @JsonProperty("UserGPF3")
    private String userGpf3;

    @JsonProperty("UserGPF4")
    private String userGpf4;

    @JsonProperty("UserGPF5")
    private String userGpf5;

    @JsonProperty("UserCardSerialNumber")
    private String userCardSerialNumber;

    @JsonProperty("UserCardID")
    private String userCardId;

    @JsonProperty("DoorName")
    private String doorName;

    @JsonProperty("DoorExtID")
    private String doorExtId;

    @JsonProperty("DoorGPF1")
    private String doorGpf1;

    @JsonProperty("DoorGPF2")
    private String doorGpf2;

    @Override
    public String getType() {
        return SaltoEventTypes.EVENT_STREAM.code();
    }
}
