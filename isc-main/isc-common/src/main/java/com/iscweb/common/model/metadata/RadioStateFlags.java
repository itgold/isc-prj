package com.iscweb.common.model.metadata;

/**
 * Radio device state flags.
 *
 * @see com.iscweb.common.model.dto.entity.core.RadioDto::deviceState field.
 */
public enum RadioStateFlags {
    ACTIVE(0x1),
    GPS_ACTIVE(0x2),
    POLICY_MODE(0x8),
    LOCKED(0x10),
    GPS_TRIGGER(0x20),
    LOCK_WAIT(0x40),
    I_BEACON_ACTIVE(0x100),
    KILLED(0x200),
    DISABLE_DATA(0x400),
    DISABLE_VOICE(0x800),
    WIFI_ACTIVE(0x1000),
    EMERGENCY(0x2000);

    private final int code;

    RadioStateFlags(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static boolean isActive(int deviceState) {
        return (deviceState & RadioStateFlags.ACTIVE.code) != 0;
    }

    public static boolean isLocked(int deviceState) {
        return (deviceState & RadioStateFlags.LOCKED.code) != 0 || (deviceState & RadioStateFlags.LOCK_WAIT.code) != 0;
    }

    public static boolean isKilled(int deviceState) {
        return (deviceState & RadioStateFlags.KILLED.code) != 0;
    }
}
