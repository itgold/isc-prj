package com.iscweb.common.util;

public final class ValidationUtils {

    public static void isNotEmpty(String text, String errorMessage) {
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void isNotNull(Object text, String errorMessage) {
        if (null == text) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
