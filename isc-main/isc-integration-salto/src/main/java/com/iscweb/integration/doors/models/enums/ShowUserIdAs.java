package com.iscweb.integration.doors.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ShowUserIdAs implements ISaltoEnum {
    USERNAME(0, "User name (first name + last name + etc)"),
    EXT_USER_ID(1, "ExtUserID"),
    CARD_SERIAL(2, "Card’s serial number.");

    private final int code;
    private final String message;

    ShowUserIdAs(final int code, final String message) {
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
    public static ShowUserIdAs forValue(int code) {
        ShowUserIdAs result = ShowUserIdAs.EXT_USER_ID;
        for (ShowUserIdAs type : values()) {
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
