package com.iscweb.common.exception;

import com.iscweb.common.exception.util.ErrorCode;

import static com.iscweb.common.exception.util.ErrorCode.ERROR_SERVER_ERROR;

/**
 * An exception for public, API-level application errors.
 */
public class PublicApiException extends ServiceException {

    public PublicApiException() {
        super();
        setErrorCode(ERROR_SERVER_ERROR);
    }

    public PublicApiException(String message) {
        super(message);
        setErrorCode(ERROR_SERVER_ERROR);
    }

    public PublicApiException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public PublicApiException(String message, Throwable cause) {
        super(message, cause);
        setErrorCode(ERROR_SERVER_ERROR);
    }

    public PublicApiException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public PublicApiException(Throwable cause) {
        super(cause);
        setErrorCode(ERROR_SERVER_ERROR);
    }

    public PublicApiException(ServiceException cause) {
        super(cause);
        setErrorCode(cause.getErrorCode());
    }

    public PublicApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        setErrorCode(ERROR_SERVER_ERROR);
    }
}
