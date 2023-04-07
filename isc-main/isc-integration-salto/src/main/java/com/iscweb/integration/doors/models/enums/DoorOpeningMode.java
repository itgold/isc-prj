package com.iscweb.integration.doors.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DoorOpeningMode implements ISaltoEnum {
    STANDARD(0,
            "The lock will only open if you present an authorised card.",
            com.iscweb.common.model.metadata.DoorOpeningMode.STANDARD),
    OFFICE(1,
            "The door remains opened all the time",
            com.iscweb.common.model.metadata.DoorOpeningMode.OFFICE),
    TIMED_OFFICE(2,
            "Office mode is allowed at certain time periods along the day. Automatically return to STANDARD mode.",
            com.iscweb.common.model.metadata.DoorOpeningMode.TIMED_OFFICE),
    AUTOMATIC_OPENING(3,
            "The lock is automatically opened at certain instants along the day and remains opened during a programmable period.",
            com.iscweb.common.model.metadata.DoorOpeningMode.AUTOMATIC_OPENING),
    TOGGLE(4, "",
            com.iscweb.common.model.metadata.DoorOpeningMode.TOGGLE),
    TIMED_TOGGLE(5, "",
            com.iscweb.common.model.metadata.DoorOpeningMode.TIMED_TOGGLE),
    KEYPAD_ONLY(6,
            "The door can be opened by just typing a valid PIN code on its keypad (no card required).",
            com.iscweb.common.model.metadata.DoorOpeningMode.KEYPAD_ONLY),
    TIMED_KEYPAD(7, "",
            com.iscweb.common.model.metadata.DoorOpeningMode.TIMED_KEYPAD),
    KEY_AND_PIN(8,
            "An authorized card and a valid PIN code typed on the keypad",
            com.iscweb.common.model.metadata.DoorOpeningMode.KEY_AND_PIN),
    TIMED_KEY_AND_PIN(9 , "",
            com.iscweb.common.model.metadata.DoorOpeningMode.TIMED_KEY_AND_PIN),
    AUTOMATIC_OPENING_OFFICE(10, "",
            com.iscweb.common.model.metadata.DoorOpeningMode.AUTOMATIC_OPENING_OFFICE),
    AUTOMATIC_OPENING_TOGGLE(11, "",
            com.iscweb.common.model.metadata.DoorOpeningMode.AUTOMATIC_OPENING_TOGGLE),
    AUTOMATIC_CHANGES(12, "",
            com.iscweb.common.model.metadata.DoorOpeningMode.AUTOMATIC_CHANGES),
    EXIT_LEAVES_OPEN(13, "",
            com.iscweb.common.model.metadata.DoorOpeningMode.EXIT_LEAVES_OPEN),
    TOGGLE_EXIT_LEAVES_OPEN(14, "",
            com.iscweb.common.model.metadata.DoorOpeningMode.TOGGLE_EXIT_LEAVES_OPEN),
    KEYPAD_EXIT_LEAVES_OPEN(15, "",
            com.iscweb.common.model.metadata.DoorOpeningMode.KEYPAD_EXIT_LEAVES_OPEN);

    private final int code;
    private final String message;
    private final com.iscweb.common.model.metadata.DoorOpeningMode dtoValue;

    DoorOpeningMode(final int code, final String message, final com.iscweb.common.model.metadata.DoorOpeningMode dtoValue) {
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
    public static DoorOpeningMode forValue(int code) {
        DoorOpeningMode result = DoorOpeningMode.STANDARD;
        for (DoorOpeningMode type : values()) {
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
    public com.iscweb.common.model.metadata.DoorOpeningMode toDto() {
        return dtoValue;
    }
}
