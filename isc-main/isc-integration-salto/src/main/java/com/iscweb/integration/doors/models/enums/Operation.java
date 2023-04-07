package com.iscweb.integration.doors.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Operation implements ISaltoEnum {
    SYNC(-1, "Synchronization"),
    UNKNOWN(0, "Unknown"),
    OPENING_OR_CLOSING(1, "Opening or closing."),

    // Openings and closings:
    DOOR_OPENED_INSIDE_HANDLE(16, "Door opened: inside handle."),
    DOOR_OPENED_KEY(17, "Door opened: key."),
    DOOR_OPENED_KEY_AND_KEYBOARD(18, "Door opened: key and keyboard."),
    DOOR_OPENED_MULTI_GUEST_KEY(19, "Door opened: multiple guest key."),
    DOOR_OPENED_UNIQUE(20, "Door opened: unique opening."),
    DOOR_OPENED_SWITCH(21, "Door opened: switch."),
    DOOR_OPENED_MECHANICAL_KEY(22, "Door opened: mechanical key."),
    FIRST_DOUBLE_CARD_READ(23, "First double card read"),
    DOOR_OPENED_AFTER_SECOND_DOUBLE_CARD_PRESENTED(24, "Door opened after second double card presented."),
    DOOR_OPENED_PPD(25, "Door opened: PPD."),
    DOOR_OPENED_KEYBOARD(26, "Door opened: keyboard."),
    DOOR_OPENED_SPARE_CARD(27, "Door opened: spare card (hotel)."),
    DOOR_OPENED_ONLINE_COMMAND(28, "Door opened: online command."),
    DOOR_MOST_PROBABLY_OPENED_KEY_AND_PIN(29, "Door most probably opened: key and PIN."),
    DOOR_CLOSED_KEY(33, "Door closed: key."),
    DOOR_CLOSED_KEY_AND_KEYBOARD(34, "Door closed: key and keyboard."),
    DOOR_CLOSED_KEYBOARD(35, "Door closed: keyboard."),
    DOOR_CLOSED_SWITCH(36, "Door closed: switch."),

    // Actions:
    KEY_INSERTED(37, "Key inserted (energy saving device)."),
    KEY_REMOVED(38, "Key removed (energy saving device)."),
    ROOM_PREPARED(39, "Room prepared (energy saving device)."),
    START_OF_PRIVACY(40, "Start of privacy."),
    END_OF_PRIVACY(41, "End of privacy."),

    // Door status changes:
    END_OF_OFFICE_MODE_KEYPAD(32, "End of office mode (keypad)."),
    COMMUNICATION_WITH_THE_READER_LOST(47, "Communication with the reader lost."),
    COMMUNICATION_WITH_THE_READER_REESTABLISHED(48, "Communication with the reader reestablished."),
    START_OF_OFFICE_MODE(49, "Start of office mode."),
    END_OF_OFFICE_MODE(50, "End of office mode."),
    HOTEL_GUEST_CANCELLED(51, "Hotel guest cancelled."),
    DOOR_PROGRAMMED_WITH_SPARE_KEY(54, "Door programmed with spare key."),
    NEW_HOTEL_GUEST_KEY(55, "New hotel guest key."),
    COMMUNICATION_WITH_SALTO_SOFTWARE_LOST(79, "Door has lost communication with the Salto software."),
    COMMUNICATION_WITH_SALTO_SOFTWARE_ESTABLISHED(80, "Door has re-established communication with the Salto software."),
    START_OF_OFFICE_MODE_ONLINE(65, "Start of office mode (online)."),
    END_OF_OFFICE_MODE_ONLINE(66, "End of office mode (online)."),
    AUTOMATIC_CHANGE(68, "Automatic change."),
    COMMUNICATION_REESTABLISHED(1000, "The Salto software has re-established communication with the door."),
    COMMUNICATION_LOST(1001, "The Salto software has lost communication with the door."),

    // Online commands from host:
    START_OF_FORCE_OPEN_ONLINE(56, "Start of forced opening (online)."),
    END_OF_FORCE_OPEN_ONLINE(57, "End of forced opening (online)."),
    START_OF_FORCED_CLOSING_ONLINE(58, "Start of forced closing (online)."),
    END_OF_FORCED_CLOSING_ONLINE(59, "End of forced closing (online)."),
    ONLINE_PERIPHERAL_UPDATED(72, "Online peripheral updated."),
    RF_LOCK_UPDATED(118, "RF Lock updated."),
    RF_LOCK_RESTARTED(120, "Lock restarted."),

    // Key modifications (mostly in online units):
    NEW_RENOVATION_CODE_FOR_KEY_ONLINE(8, "New renovation code for key (online)."),
    KEY_UPDATED_IN_OUT_OF_SITE_MODE_ONLINE(69, "Key updated in \"out of site\" mode (online)."),
    KEY_UPDATED_ONLINE(76, "Key updated (online)."),
    KEY_HAS_NOT_BEEN_COMPLETELY_UPDATED_ONLINE(99, "Warning: key has not been completely updated (online)."),
    KEY_DELETED_ONLINE(78, "Key deleted (online)."),
    EXPIRATION_AUTOMATICALLY_EXTENDED_OFFLINE(70, "Expiration automatically extended (offline)."),
    BLACKLISTED_KEY_DELETED(104, "Blacklisted key deleted."),
    GUEST_NEW_KEY(2000, "Guest new key."),
    GUEST_COPY_KEY(2001, "Guest copy key."),

    // Rejections:
    OPENING_NOT_ALLOWED_KEY_NO_ACTIVATED(81, "Opening not allowed: key no activated."),
    OPENING_NOT_ALLOWED_KEY_EXPIRED(82, "Opening not allowed: key expired."),
    OPENING_NOT_ALLOWED_KEY_OUT_OF_DATE(83, "Opening not allowed: key out of date."),
    OPENING_NOT_ALLOWED_KEY_NOT_ALLOWED_IN_THIS_DOOR(84, "Opening not allowed: key not allowed in this door."),
    OPENING_NOT_ALLOWED_OUT_OF_TIME(85, "Opening not allowed: out of time."),
    OPENING_NOT_ALLOWED_KEY_DOES_NOT_OVERRIDE_PRIVACY(87, "Opening not allowed: key does not override privacy."),
    OPENING_NOT_ALLOWED_OLD_HOTEL_GUEST_KEY(88, "Opening not allowed: old hotel guest key."),
    OPENING_NOT_ALLOWED_HOTEL_GUEST_KEY_CANCELLED(89, "Opening not allowed: hotel guest key cancelled."),
    OPENING_NOT_ALLOWED_ANTIPASSBACK(90, "Opening not allowed: antipassback."),
    OPENING_NOT_ALLOWED_SECOND_DOUBLE_CARD_NOT_PRESENTED(91, "Opening not allowed: second double card not presented."),
    OPENING_NOT_ALLOWED_NO_ASSOCIATED_AUTHORIZATION(92, "Opening not allowed: no associated authorization."),
    OPENING_NOT_ALLOWED_INVALID_PIN(93, "Opening not allowed: invalid PIN."),
    OPENING_NOT_ALLOWED_DOOR_IN_EMERGENCY_STATE(95, "Opening not allowed: door in emergency state."),
    OPENING_NOT_ALLOWED_KEY_CANCELLED(96, "Opening not allowed: key cancelled."),
    OPENING_NOT_ALLOWED_UNIQUE_OPENING_KEY_ALREADY_USED(97, "Opening not allowed: unique opening key already used."),
    OPENING_NOT_ALLOWED_KEY_WITH_OLD_RENOVATION_NUMBER(98, "Opening not allowed: key with old renovation number."),
    OPENING_NOT_ALLOWED_RUN_OUT_OF_BATTERY(100, "Opening not allowed: run out of battery."),
    OPENING_NOT_ALLOWED_UNABLE_TO_AUDIT_ON_THE_KEY(101, "Opening not allowed: unable to audit on the key."),
    OPENING_NOT_ALLOWED_LOCKER_OCCUPANCY_TIMEOUT(102, "Opening not allowed: locker occupancy timeout."),
    OPENING_NOT_ALLOWED_DENIED_BY_HOST(103, "Opening not allowed: denied by host."),
    OPENING_NOT_ALLOWED_KEY_WITH_DATA_MANIPULATED(107, "Opening not allowed: key with data manipulated."),
    CLOSING_NOT_ALLOWED_DOOR_IN_EMERGENCY_STATE(111, "Closing not allowed: door in emergency state."),

    // Maintenance:
    NEW_RENOVATION_CODE(112, "New renovation code."),
    PPD_CONNECTION(113, "PPD connection."),
    TIME_MODIFIED_DAYLIGHT_SAVING_TIME(114, "Time modified (daylight saving time)."),
    LOW_BATTERY_LEVEL(115, "Low battery level."),
    INCORRECT_CLOCK_VALUE(116, "Incorrect clock value."),
    RF_LOCK_DATE_AND_TIME_UPDATED(117, "RF Lock date and time updated."),
    UNABLE_TO_PERFORM_OPEN_CLOSE_HARDWARE_FAILURE(119, "Unable to perform open/close operation due to a hardware failure."),

    // Alarms and warnings:
    DURESS_ALARM(42, "“Duress” alarm."),
    ALARM_INTRUSION_ONLINE(60, "Alarm: intrusion (online)."),
    ALARM_TAMPER_ONLINE(61, "Alarm: tamper (online)."),
    DOOR_LEFT_OPENED_DLO(62, "Door left opened (DLO)."),
    END_OF_DLO_DOOR_LEFT_OPENED(63, "End of DLO (door left opened)."),
    END_OF_INTRUSION(64, "End of intrusion."),
    END_OF_TAMPER(67, "End of tamper.")
    ;

    private final int code;
    private final String message;

    Operation(final int code, final String message) {
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
    public static Operation forValue(int code) {
        Operation result = Operation.UNKNOWN;
        for (Operation value : values()) {
            if (value.code == code) {
                result = value;
            }
        }

        return result;
    }

    @JsonValue
    public int toValue() {
        return this.getCode();
    }
}
