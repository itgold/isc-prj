package com.iscweb.integration.doors.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.iscweb.common.model.metadata.DoorBatteryStatus;

public enum BatteryStatus implements ISaltoEnum {
    UNKNOWN(-1, "Unknown", DoorBatteryStatus.UNKNOWN),
    NORMAL(0, "normal", DoorBatteryStatus.NORMAL),
    LOW(1, "low", DoorBatteryStatus.LOW),
    VERY_LOW(2, "very low", DoorBatteryStatus.VERY_LOW);

    private final int code;
    private final String message;
    private final DoorBatteryStatus dtoValue;

    BatteryStatus(final int code, final String message, final DoorBatteryStatus dtoValue) {
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
    public static BatteryStatus forValue(int code) {
        BatteryStatus result = BatteryStatus.UNKNOWN;
        for (BatteryStatus type : values()) {
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
    public DoorBatteryStatus toDto() {
        return dtoValue;
    }
}
