package com.iscweb.common.sis.exceptions;

import com.iscweb.common.exception.ServiceException;

/**
 * Simple integration system connection exception is thrown when application
 * can't obtain a connection to the target system.
 */
public class SisConnectionException extends ServiceException {

    public SisConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
