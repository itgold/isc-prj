package com.iscweb.common.model.metadata;

/**
 * Door opening mode.
 *
 * @author dmorozov
 * Date: 4/28/19
 */
public enum DoorOpeningMode {
    STANDARD,
    OFFICE,
    TIMED_OFFICE,
    AUTOMATIC_OPENING,
    TOGGLE,
    TIMED_TOGGLE,
    KEYPAD_ONLY,
    TIMED_KEYPAD,
    KEY_AND_PIN,
    TIMED_KEY_AND_PIN,
    AUTOMATIC_OPENING_OFFICE,
    AUTOMATIC_OPENING_TOGGLE,
    AUTOMATIC_CHANGES,
    EXIT_LEAVES_OPEN,
    TOGGLE_EXIT_LEAVES_OPEN,
    KEYPAD_EXIT_LEAVES_OPEN
}
