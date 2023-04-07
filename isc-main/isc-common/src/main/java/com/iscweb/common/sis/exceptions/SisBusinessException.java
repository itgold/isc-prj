package com.iscweb.common.sis.exceptions;

import com.iscweb.common.exception.ServiceException;

/**
 * Simple integration service generic exception.
 */
public class SisBusinessException extends ServiceException {

    public SisBusinessException(String msg) {
        super(msg);
    }

    public SisBusinessException(String msg, Throwable e) {
        super(msg, e);
    }
}
