package com.iscweb.integration.doors.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ShowDoorAs implements ISaltoEnum {
    DOOR_NAME(0, "Door name"),
    EXT_DOOR_ID(1, "ExtDoorID");

    private final int code;
    private final String message;

    ShowDoorAs(final int code, final String message) {
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
    public static ShowDoorAs forValue(int code) {
        ShowDoorAs result = ShowDoorAs.EXT_DOOR_ID;
        for (ShowDoorAs type : values()) {
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
