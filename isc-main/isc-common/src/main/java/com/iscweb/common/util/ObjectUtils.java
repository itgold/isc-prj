package com.iscweb.common.util;

public final class ObjectUtils {

    /**
     * An Id of the default region.
     */
    public static final long ID_NONE = 0;

    public static boolean getBoolean(Boolean value) {
        return ObjectUtils.getBoolean(value, false);
    }

    public static boolean getBoolean(Boolean value, boolean defaultValue) {
        return value != null ? value : defaultValue;
    }
}
