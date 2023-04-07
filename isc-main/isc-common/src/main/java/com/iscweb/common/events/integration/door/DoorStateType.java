package com.iscweb.common.events.integration.door;

/**
 * Enumeration defines all sub-states for a door device.
 * Combination of sub-states represents the whole device state.
 */
public enum DoorStateType {
    MODE, COMMUNICATION, HARDWARE, REJECTION, STATUS, BATTERY, INTRUSION, ALERTS, SYNC
}
