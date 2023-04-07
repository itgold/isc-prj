package com.iscweb.common.exception;

import com.iscweb.common.exception.util.ErrorCode;

/**
 * An exception that is used when the requested operation is invalid.
 * For instance, the passed arguments may be of the wrong type, or not appropriate for
 * the operation to be completed successfully. The requester should be able to 'fix'
 * this by providing the correct arguments to the operation that raised this exception.
 */
public class InvalidOperationException extends ServiceException {

    public InvalidOperationException(String message) {
        super(message, ErrorCode.ERROR_CODE_BAD_REQUEST);
    }
}
