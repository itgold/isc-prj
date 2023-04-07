package com.iscweb.common.exception.util;

import org.springframework.boot.logging.LogLevel;

public class ExceptionHandlingUtils {

    /**
     * Returns the appropriate log level for the given error's status code. Currently 400 (Bad Request),
     * 404 (Not Found), and 416 (Range Not Satisfiable) will be logged as INFO. 401 (Unauthorized) and
     * 403 (Forbidden) will be logged as WARN. Everything else is either not captured or logged as ERROR.
     */
    public static LogLevel getErrorLogLevel(Integer statusCode) {
        LogLevel result;
        switch (statusCode) {
            case 400:
            case 404:
            case 416:
                result = LogLevel.INFO;
                break;
            case 401:
            case 403:
                result = LogLevel.WARN;
                break;
            default:
                result = LogLevel.ERROR;
                break;
        }
        return result;
    }

}
