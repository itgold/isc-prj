package com.iscweb.common.exception;

import com.iscweb.common.exception.util.ErrorCode;

/**
 * An application service layer exception.
 * By default a new instance of this error will trigger a 500 SERVER ERROR to show up
 * on the app; this is default because it is an indication that something bad happened,
 * even though we may not be aware of what it is. The application can override this
 * value with the most appropriate code.
 */
public class ServiceException extends BaseApplicationException {

    public ServiceException() {
        setErrorCode(ErrorCode.ERROR_SERVER_ERROR);
    }

    public ServiceException(String message) {
        this(message, ErrorCode.ERROR_SERVER_ERROR);
    }

    public ServiceException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
