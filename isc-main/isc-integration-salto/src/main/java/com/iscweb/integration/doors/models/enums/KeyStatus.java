package com.iscweb.integration.doors.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum KeyStatus implements ISaltoEnum {
    NOT_APPLICABLE(0, "Not applicable"),
    SYNCHRONIZED(1, "No update required"),
    UPDATE_RECOMMENDED(2, "Update recommended"),
    UPDATE_REQUIRED(3, "Update required"),
    REEDITION_REQUIRED(4, "Reedition required"),
    KEY_EXPIRED(5, "Key expired");

    private final int code;
    private final String message;

    KeyStatus(final int code, final String message) {
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
    public static KeyStatus forValue(int code) {
        KeyStatus result = KeyStatus.NOT_APPLICABLE;
        for (KeyStatus type : values()) {
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
