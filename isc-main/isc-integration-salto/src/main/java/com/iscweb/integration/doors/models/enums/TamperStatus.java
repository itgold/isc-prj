package com.iscweb.integration.doors.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.iscweb.common.model.metadata.DoorTamperStatus;

public enum TamperStatus implements ISaltoEnum {
    UNKNOWN(-1, "Unknown", DoorTamperStatus.UNKNOWN),
    NORMAL(0, "normal", DoorTamperStatus.NORMAL),
    ALARMED(1, "alarmed", DoorTamperStatus.ALARMED);

    private final int code;
    private final String message;
    private final DoorTamperStatus dtoValue;

    TamperStatus(final int code, final String message, final DoorTamperStatus dtoValue) {
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
    public static TamperStatus forValue(int code) {
        TamperStatus result = TamperStatus.UNKNOWN;
        for (TamperStatus type : values()) {
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
    public DoorTamperStatus toDto() {
        return dtoValue;
    }
}
