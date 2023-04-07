package com.iscweb.integration.doors.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DoorType implements ISaltoEnum {
    UNKNOWN(-1, "Unknown"),
    CONTROL_UNIT(0, "Control Unit (IP door)"),
    RF_WIRELESS(1, "RF wireless escutcheon.");

    private final int code;
    private final String message;

    DoorType(final int code, final String message) {
        this.code = code;
        this.message = message;
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
    public static DoorType forValue(int code) {
        DoorType result = DoorType.UNKNOWN;
        for (DoorType type : values()) {
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
}
