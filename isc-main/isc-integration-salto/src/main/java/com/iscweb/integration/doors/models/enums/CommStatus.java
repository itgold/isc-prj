package com.iscweb.integration.doors.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.iscweb.common.model.metadata.DoorConnectionStatus;

public enum CommStatus implements ISaltoEnum {
    UNKNOWN(-1, "Unknown", DoorConnectionStatus.UNKNOWN),
    NO_COMMUNICATION(0, "No communication", DoorConnectionStatus.NO_COMMUNICATION),
    ONLINE(1, "communication is OK.", DoorConnectionStatus.ONLINE);

    private final int code;
    private final String message;
    private final DoorConnectionStatus dtoValue;

    CommStatus(final int code, final String message, final DoorConnectionStatus dtoValue) {
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
    public static CommStatus forValue(int code) {
        CommStatus result = CommStatus.UNKNOWN;
        for (CommStatus type : values()) {
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
    public DoorConnectionStatus toDto() {
        return dtoValue;
    }
}
