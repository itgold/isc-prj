package com.iscweb.common.exception;

import com.iscweb.common.exception.util.ErrorCode;

import java.util.List;

/**
 * Thrown when an invalid value has been passed as a parameter where a
 * restricted set of values (not including its value) are allowed.
 */
public class InvalidValueException extends BaseApplicationRuntimeException {

    protected static final ErrorCode ERROR_CODE = ErrorCode.ERROR_CODE_BAD_REQUEST;

    public InvalidValueException(String value, List<String> validValues) {
        super(getErrorMessage(value, validValues), ERROR_CODE);
    }

    public InvalidValueException(String value,
                                 List<String> validValues,
                                 Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(getErrorMessage(value, validValues),
              ERROR_CODE,
              cause,
              enableSuppression,
              writableStackTrace);
    }

    public static String getErrorMessage(String value, List<String> validValues) {
        return String.format("Invalid value '%s'.  Allowed values are: %s", value, validValues);
    }
}
