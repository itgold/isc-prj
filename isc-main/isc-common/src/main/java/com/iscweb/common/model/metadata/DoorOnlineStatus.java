package com.iscweb.common.model.metadata;

/**
 * Door online status.
 *
 * @author dmorozov
 * Date: 4/28/19
 */
public enum DoorOnlineStatus {
    UNKNOWN,
    OPENED,
    CLOSED,
    LEFT_OPENED,
    INTRUSION,
    EMERGENCY_OPEN,
    EMERGENCY_CLOSE,
    INITIALIZING
}
