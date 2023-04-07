package com.iscweb.common.exception;

import com.iscweb.common.exception.util.ErrorCode;

/**
 * An exception for a missing resource. These exceptions are triggered when an element,
 * usually persisted in a database, cannot be found with the identifier provided by the
 * requester.
 */
public class ResourceNotFoundException extends ServiceException {

    public ResourceNotFoundException(String message) {
        super(message, ErrorCode.ERROR_CODE_NO_CONTENT);
    }
}
