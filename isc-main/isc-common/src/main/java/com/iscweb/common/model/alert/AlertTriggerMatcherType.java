package com.iscweb.common.model.alert;

/**
 * Alert trigger matcher type.
 * This is for convenience to help process alert triggers more optimized way.
 */
public enum AlertTriggerMatcherType {
    /**
     * Match by date or/and time only
     */
    DATE_TIME,
    /**
     * Custom matcher based on event or device from <code>AlertMatchingContext</code>
     */
    CUSTOM
}
