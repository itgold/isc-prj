package com.iscweb.integration.doors.exceptions;

import com.iscweb.common.sis.exceptions.SisParsingException;

/**
 * An error caused by Salto response parsing.
 */
public class SaltoParseResponseException extends SisParsingException {

    public SaltoParseResponseException(String msg) {
        super(msg);
    }

    public SaltoParseResponseException(String msg, Throwable e) {
        super(msg, e);
    }
}
