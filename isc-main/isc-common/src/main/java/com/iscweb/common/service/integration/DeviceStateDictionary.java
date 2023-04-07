package com.iscweb.common.service.integration;

public enum DeviceStateDictionary {
    SYNC(-1, "SYNC", "Synchronization"),
    UNKNOWN(0, "UNKNOWN", "Unknown"),
    OPENING_OR_CLOSING(1, "TOGGLE", "Opening or closing."),

    // Openings and closings:
    DOOR_OPENED_INSIDE_HANDLE(16, "OPEN", "Door opened: inside handle."),
    DOOR_OPENED_KEY(17, "OPEN", "Door opened: key."),
    DOOR_OPENED_KEY_AND_KEYBOARD(18, "OPEN", "Door opened: key and keyboard."),
    DOOR_OPENED_MULTI_GUEST_KEY(19, "OPEN", "Door opened: multiple guest key."),
    DOOR_OPENED_UNIQUE(20, "OPEN", "Door opened: unique opening."),
    DOOR_OPENED_SWITCH(21, "OPEN", "Door opened: switch."),
    DOOR_OPENED_MECHANICAL_KEY(22, "OPEN", "Door opened: mechanical key."),
    FIRST_DOUBLE_CARD_READ(23, "DBL CARD", "First double card read"),
    DOOR_OPENED_AFTER_SECOND_DOUBLE_CARD_PRESENTED(24, "OPEN", "Door opened after second double card presented."),
    DOOR_OPENED_PPD(25, "OPEN", "Door opened: PPD."),
    DOOR_OPENED_KEYBOARD(26, "OPEN", "Door opened: keyboard."),
    DOOR_OPENED_SPARE_CARD(27, "OPEN", "Door opened: spare card (hotel)."),
    DOOR_OPENED_ONLINE_COMMAND(28, "OPEN", "Door opened: online command."),
    DOOR_MOST_PROBABLY_OPENED_KEY_AND_PIN(29, "OPEN", "Door most probably opened: key and PIN."),
    DOOR_CLOSED_KEY(33, "CLOSE", "Door closed: key."),
    DOOR_CLOSED_KEY_AND_KEYBOARD(34, "CLOSE", "Door closed: key and keyboard."),
    DOOR_CLOSED_KEYBOARD(35, "CLOSE", "Door closed: keyboard."),
    DOOR_CLOSED_SWITCH(36, "CLOSE", "Door closed: switch."),

    // Actions:
    KEY_INSERTED(37, "KEY", "Key inserted (energy saving device)."),
    KEY_REMOVED(38, "KEY", "Key removed (energy saving device)."),
    ROOM_PREPARED(39, "PREPARED", "Room prepared (energy saving device)."),
    START_OF_PRIVACY(40, "PRIVACY", "Start of privacy."),
    END_OF_PRIVACY(41, "PRIVACY", "End of privacy."),

    // Door status changes:
    END_OF_OFFICE_MODE_KEYPAD(32, "OFFICE", "End of office mode (keypad)."),
    COMMUNICATION_WITH_THE_READER_LOST(47, "OFFLINE", "Communication with the reader lost."),
    COMMUNICATION_WITH_THE_READER_REESTABLISHED(48, "ONLINE", "Communication with the reader reestablished."),
    START_OF_OFFICE_MODE(49, "OFFICE", "Start of office mode."),
    END_OF_OFFICE_MODE(50, "OFFICE", "End of office mode."),
    HOTEL_GUEST_CANCELLED(51, "GUEST", "Hotel guest cancelled."),
    DOOR_PROGRAMMED_WITH_SPARE_KEY(54, "PROGRAMMED", "Door programmed with spare key."),
    NEW_HOTEL_GUEST_KEY(55, "NEW KEY", "New hotel guest key."),
    COMMUNICATION_WITH_SALTO_SOFTWARE_LOST(79, "OFFLINE", "Door has lost communication with the Salto software."),
    COMMUNICATION_WITH_SALTO_SOFTWARE_ESTABLISHED(80, "ONLINE", "Door has re-established communication with the Salto software."),
    START_OF_OFFICE_MODE_ONLINE(65, "OFFICE", "Start of office mode (online)."),
    END_OF_OFFICE_MODE_ONLINE(66, "OFFICE", "End of office mode (online)."),
    AUTOMATIC_CHANGE(68, "AUTO", "Automatic change."),
    COMMUNICATION_REESTABLISHED(1000, "ONLINE", "The Salto software has re-established communication with the door."),
    COMMUNICATION_LOST(1001, "OFFLINE", "The Salto software has lost communication with the door."),

    // Online commands from host:
    START_OF_FORCE_OPEN_ONLINE(56, "FORCE OPEN", "Start of forced opening (online)."),
    END_OF_FORCE_OPEN_ONLINE(57, "FORCE OPEN", "End of forced opening (online)."),
    START_OF_FORCED_CLOSING_ONLINE(58, "FORCE CLOSE", "Start of forced closing (online)."),
    END_OF_FORCED_CLOSING_ONLINE(59, "FORCE CLOSE", "End of forced closing (online)."),
    ONLINE_PERIPHERAL_UPDATED(72, "UPDATED", "Online peripheral updated."),
    RF_LOCK_UPDATED(118, "UPDATED", "RF Lock updated."),
    RF_LOCK_RESTARTED(120, "RESTARTED", "Lock restarted."),

    // Key modifications (mostly in online units):
    NEW_RENOVATION_CODE_FOR_KEY_ONLINE(8, "NEW CODE", "New renovation code for key (online)."),
    KEY_UPDATED_IN_OUT_OF_SITE_MODE_ONLINE(69, "UPDATED", "Key updated in \"out of site\" mode (online)."),
    KEY_UPDATED_ONLINE(76, "UPDATED", "Key updated (online)."),
    KEY_HAS_NOT_BEEN_COMPLETELY_UPDATED_ONLINE(99, "UPDATED", "Warning: key has not been completely updated (online)."),
    KEY_DELETED_ONLINE(78, "DELETED", "Key deleted (online)."),
    EXPIRATION_AUTOMATICALLY_EXTENDED_OFFLINE(70, "UPDATED", "Expiration automatically extended (offline)."),
    BLACKLISTED_KEY_DELETED(104, "DELETED", "Blacklisted key deleted."),
    GUEST_NEW_KEY(2000, "GUEST", "Guest new key."),
    GUEST_COPY_KEY(2001, "GUEST", "Guest copy key."),

    // Rejections:
    OPENING_NOT_ALLOWED_KEY_NO_ACTIVATED(81, "REJECTED", "Opening not allowed: key no activated."),
    OPENING_NOT_ALLOWED_KEY_EXPIRED(82, "REJECTED", "Opening not allowed: key expired."),
    OPENING_NOT_ALLOWED_KEY_OUT_OF_DATE(83, "REJECTED", "Opening not allowed: key out of date."),
    OPENING_NOT_ALLOWED_KEY_NOT_ALLOWED_IN_THIS_DOOR(84, "REJECTED", "Opening not allowed: key not allowed in this door."),
    OPENING_NOT_ALLOWED_OUT_OF_TIME(85, "REJECTED", "Opening not allowed: out of time."),
    OPENING_NOT_ALLOWED_KEY_DOES_NOT_OVERRIDE_PRIVACY(87, "REJECTED", "Opening not allowed: key does not override privacy."),
    OPENING_NOT_ALLOWED_OLD_HOTEL_GUEST_KEY(88, "REJECTED", "Opening not allowed: old hotel guest key."),
    OPENING_NOT_ALLOWED_HOTEL_GUEST_KEY_CANCELLED(89, "REJECTED", "Opening not allowed: hotel guest key cancelled."),
    OPENING_NOT_ALLOWED_ANTIPASSBACK(90, "REJECTED", "Opening not allowed: antipassback."),
    OPENING_NOT_ALLOWED_SECOND_DOUBLE_CARD_NOT_PRESENTED(91, "REJECTED", "Opening not allowed: second double card not presented."),
    OPENING_NOT_ALLOWED_NO_ASSOCIATED_AUTHORIZATION(92, "REJECTED", "Opening not allowed: no associated authorization."),
    OPENING_NOT_ALLOWED_INVALID_PIN(93, "REJECTED", "Opening not allowed: invalid PIN."),
    OPENING_NOT_ALLOWED_DOOR_IN_EMERGENCY_STATE(95, "REJECTED", "Opening not allowed: door in emergency state."),
    OPENING_NOT_ALLOWED_KEY_CANCELLED(96, "REJECTED", "Opening not allowed: key cancelled."),
    OPENING_NOT_ALLOWED_UNIQUE_OPENING_KEY_ALREADY_USED(97, "REJECTED", "Opening not allowed: unique opening key already used."),
    OPENING_NOT_ALLOWED_KEY_WITH_OLD_RENOVATION_NUMBER(98, "REJECTED", "Opening not allowed: key with old renovation number."),
    OPENING_NOT_ALLOWED_RUN_OUT_OF_BATTERY(100, "REJECTED", "Opening not allowed: run out of battery."),
    OPENING_NOT_ALLOWED_UNABLE_TO_AUDIT_ON_THE_KEY(101, "REJECTED", "Opening not allowed: unable to audit on the key."),
    OPENING_NOT_ALLOWED_LOCKER_OCCUPANCY_TIMEOUT(102, "REJECTED", "Opening not allowed: locker occupancy timeout."),
    OPENING_NOT_ALLOWED_DENIED_BY_HOST(103, "REJECTED", "Opening not allowed: denied by host."),
    OPENING_NOT_ALLOWED_KEY_WITH_DATA_MANIPULATED(107, "REJECTED", "Opening not allowed: key with data manipulated."),
    CLOSING_NOT_ALLOWED_DOOR_IN_EMERGENCY_STATE(111, "REJECTED", "Closing not allowed: door in emergency state."),

    // Maintenance:
    NEW_RENOVATION_CODE(112, "UPDATED", "New renovation code."),
    PPD_CONNECTION(113, "TECH", "PPD connection."),
    TIME_MODIFIED_DAYLIGHT_SAVING_TIME(114, "UPDATED", "Time modified (daylight saving time)."),
    LOW_BATTERY_LEVEL(115, "BATTERY LOW", "Low battery level."),
    INCORRECT_CLOCK_VALUE(116, "CLOCK", "Incorrect clock value."),
    RF_LOCK_DATE_AND_TIME_UPDATED(117, "UPDATED", "RF Lock date and time updated."),
    UNABLE_TO_PERFORM_OPEN_CLOSE_HARDWARE_FAILURE(119, "FAILURE", "Unable to perform open/close operation due to a hardware failure."),

    // Alarms and warnings:
    DURESS_ALARM(42, "DURESS", "“Duress” alarm."),
    ALARM_INTRUSION_ONLINE(60, "INTRUSION", "Alarm: intrusion (online)."),
    ALARM_TAMPER_ONLINE(61, "TAMPER", "Alarm: tamper (online)."),
    DOOR_LEFT_OPENED_DLO(62, "DLO", "Door left opened (DLO)."),
    END_OF_DLO_DOOR_LEFT_OPENED(63, "DLO", "End of DLO (door left opened)."),
    END_OF_INTRUSION(64, "INTRUSION", "End of intrusion."),
    END_OF_TAMPER(67, "TAMPER", "End of tamper."),

    NO_COMMUNICATION(0, "OFFLINE", "No communication"),
    ONLINE(1, "ONLINE", "communication is OK."),

    STANDARD(0, "STANDARD", "The lock will only open if you present an authorised card."),
    OFFICE(1, "OFFICE", "The door remains opened all the time"),
    TIMED_OFFICE(2, "TIMED_OFFICE", "Office mode is allowed at certain time periods along the day. Automatically return to STANDARD mode."),
    AUTOMATIC_OPENING(3, "AUTO", "The lock is automatically opened at certain instants along the day and remains opened during a programmable period."),
    TOGGLE(4, "TOGGLE", "TOGGLE"),
    TIMED_TOGGLE(5, "TIMED_TOGGLE", "TIMED_TOGGLE"),
    KEYPAD_ONLY(6, "KEYPAD_ONLY", "The door can be opened by just typing a valid PIN code on its keypad (no card required)."),
    TIMED_KEYPAD(7, "TIMED_KEYPAD", "TIMED_KEYPAD"),
    KEY_AND_PIN(8, "KEY_AND_PIN", "An authorized card and a valid PIN code typed on the keypad"),
    TIMED_KEY_AND_PIN(9, "TIMED", "TIMED_KEY_AND_PIN"),
    AUTOMATIC_OPENING_OFFICE(10, "AUTO OFFICE", "AUTOMATIC_OPENING_OFFICE"),
    AUTOMATIC_OPENING_TOGGLE(11, "AUTO TOGGLE", "AUTOMATIC_OPENING_TOGGLE"),
    AUTOMATIC_CHANGES(12, "AUTO", "AUTOMATIC_CHANGES"),
    EXIT_LEAVES_OPEN(13, "LEAVES_OPEN", "EXIT_LEAVES_OPEN"),
    TOGGLE_EXIT_LEAVES_OPEN(14, "LEAVES_OPEN", "TOGGLE_EXIT_LEAVES_OPEN"),
    KEYPAD_EXIT_LEAVES_OPEN(15, "LEAVES_OPEN", "KEYPAD_EXIT_LEAVES_OPEN"),

    OPENED(0, "OPENED", "Opened"),
    CLOSED(1, "CLOSED", "Closed"),
    LEFT_OPENED(2, "LEFT_OPENED", "Left opened. Door should be closed but it is detected as being opened."),
    INTRUSION(3, "INTRUSION", "Door should be closed but it is long time since it has been detected as being opened."),
    EMERGENCY_OPEN(4, "EMERGENCY_OPEN", "The online door has been opened in emergecy mode."),
    EMERGENCY_CLOSE(5, "EMERGENCY_CLOSE", "The online door has been closed in emergecy mode."),
    INITIALIZING(1, "INITIALIZING", "The online door is being initialized.");

    private final int code;
    private final String shortName;
    private final String description;

    DeviceStateDictionary(int code, String shortName, String description) {
        this.code = code;
        this.shortName = shortName;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }
}
