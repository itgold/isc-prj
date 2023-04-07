package com.iscweb.integration.doors.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserType implements ISaltoEnum {
    CARDHOLDER(0, "Cardholder"),
    DOOR(1, "Door"),
    OPERATOR(2, "Software operator");

    private final int code;
    private final String message;

    UserType(final int code, final String message) {
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
    public static UserType forValue(int code) {
        UserType result = UserType.CARDHOLDER;
        for (UserType type : values()) {
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
