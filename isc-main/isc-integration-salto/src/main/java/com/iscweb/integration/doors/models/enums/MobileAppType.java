package com.iscweb.integration.doors.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MobileAppType implements ISaltoEnum {
    NONE(0, "None"),
    JUSTIN_MSVN(1, "Salto’s “JustIN mSVN”"),
    JUSTIN_MOBILE(2, "Salto’s “JustIN mobile”"),
    JUSTIN_SDK(3, "Third party app using Salto’s JustIN SDK");

    private final int code;
    private final String message;

    MobileAppType(final int code, final String message) {
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
    public static MobileAppType forValue(int code) {
        MobileAppType result = MobileAppType.NONE;
        for (MobileAppType type : values()) {
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
