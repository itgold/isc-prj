package com.iscweb.common.exception;

import com.iscweb.common.exception.util.ErrorCode;
import com.iscweb.common.exception.util.ExceptionHandlingUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.logging.LogLevel;

/**
 * Base class for all application runtime exceptions.
 */
public abstract class BaseApplicationRuntimeException extends RuntimeException implements IApplicationException {

    /**
     * We are declaring an error code because we expect this exception to 'bubble' all the way
     * up to client code, where exceptions will be sent as HTTP statuses. This is initialized
     * to its default value.
     */
    @Getter
    @Setter
    protected ErrorCode errorCode = ErrorCode.ERROR_CODE_BAD_REQUEST;

    public BaseApplicationRuntimeException() {
    }

    public BaseApplicationRuntimeException(String message) {
        super(message);
    }

    public BaseApplicationRuntimeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseApplicationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseApplicationRuntimeException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BaseApplicationRuntimeException(Throwable cause) {
        super(cause);
    }

    public BaseApplicationRuntimeException(String message, Throwable cause, boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BaseApplicationRuntimeException(String message, ErrorCode errorCode, Throwable cause, boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    /**
     * @see ExceptionHandlingUtils#getErrorLogLevel(Integer)
     */
    public LogLevel getErrorLogLevel() {
        return ExceptionHandlingUtils.getErrorLogLevel(getErrorCode().getHttpCode());
    }
}
