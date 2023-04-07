package com.iscweb.integration.doors.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.iscweb.common.model.metadata.DoorOnlineStatus;

public enum DoorStatus implements ISaltoEnum {
    UNKNOWN(-1, "Unknown", DoorOnlineStatus.UNKNOWN),
    OPENED(0, "Opened", DoorOnlineStatus.OPENED),
    CLOSED(1, "Closed", DoorOnlineStatus.CLOSED),
    LEFT_OPENED(2, "Left opened. Door should be closed but it is detected as being opened.", DoorOnlineStatus.LEFT_OPENED),
    INTRUSION(3, "Door should be closed but it is long time since it has been detected as being opened.", DoorOnlineStatus.INTRUSION),
    EMERGENCY_OPEN(4, "The online door has been opened in emergecy mode.", DoorOnlineStatus.EMERGENCY_OPEN),
    EMERGENCY_CLOSE(5, "The online door has been closed in emergecy mode.", DoorOnlineStatus.EMERGENCY_CLOSE),
    INITIALIZING(99, "The online door is being initialized.", DoorOnlineStatus.INITIALIZING);

    private final int code;
    private final String message;
    private final DoorOnlineStatus dtoValue;

    DoorStatus(final int code, final String message, final DoorOnlineStatus dtoValue) {
        this.code = code;
        this.message = message;
        this.dtoValue = dtoValue;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @JsonCreator
    public static DoorStatus forValue(int code) {
        DoorStatus result = DoorStatus.UNKNOWN;
        for (DoorStatus type : values()) {
            if (type.code == code) {
                result = type;
            }
        }

        return result;
    }

    @JsonValue
    public int toValue() {
        return this.getCode();
    }

    @JsonIgnore
    public DoorOnlineStatus toDto() {
        return dtoValue;
    }
}
