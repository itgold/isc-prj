package com.iscweb.common.sis.exceptions;

/**
 * Simple integration system is throwing this exception when data parsing error occurs.
 */
public class SisParsingException extends SisBusinessException {

    public SisParsingException(String msg) {
        super(msg);
    }

    public SisParsingException(String msg, Throwable e) {
        super(msg, e);
    }
}
