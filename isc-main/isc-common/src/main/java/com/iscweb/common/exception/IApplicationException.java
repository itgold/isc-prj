package com.iscweb.common.exception;

import com.iscweb.common.exception.util.ErrorCode;

import java.io.Serializable;

/**
 * Base interface for all application-level exceptions.
 */
public interface IApplicationException extends Serializable {

    String getMessage();

    Throwable getCause();

    ErrorCode getErrorCode();

}
