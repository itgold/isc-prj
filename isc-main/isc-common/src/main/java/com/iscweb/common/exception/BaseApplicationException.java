package com.iscweb.common.exception;

import com.iscweb.common.exception.util.ErrorCode;
import com.iscweb.common.exception.util.ExceptionHandlingUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.logging.LogLevel;

/**
 * Base class for all application-level exceptions.
 */
public abstract class BaseApplicationException extends Exception implements IApplicationException {

    /**
     * We are declaring an error code because we expect this exception to 'bubble' all the way
     * up to client code, where exceptions will be sent as HTTP statuses. This is initialized
     * to its default value.
     */
    @Getter
    @Setter
    private ErrorCode errorCode = ErrorCode.ERROR_CODE_BAD_REQUEST;

    public BaseApplicationException() {
    }

    public BaseApplicationException(String message) {
        super(message);
    }

    public BaseApplicationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseApplicationException(String message, Throwable cause) {
        super(message, cause);
        attemptAdoptErrorCode(cause);
    }

    public BaseApplicationException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BaseApplicationException(Throwable cause) {
        super(cause);
        attemptAdoptErrorCode(cause);
    }

    public BaseApplicationException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public BaseApplicationException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @see ExceptionHandlingUtils#getErrorLogLevel(Integer)
     */
    public LogLevel getErrorLogLevel() {
        return ExceptionHandlingUtils.getErrorLogLevel(getErrorCode().getHttpCode());
    }

    /**
     * Used to adopt error code if constructed from another exception with an error code.
     *
     * @param cause the exception to adopt from.
     */
    private void attemptAdoptErrorCode(Throwable cause) {
        if (IApplicationException.class.isAssignableFrom(cause.getClass())) {
            setErrorCode(((IApplicationException) cause).getErrorCode());
        }
    }
}
