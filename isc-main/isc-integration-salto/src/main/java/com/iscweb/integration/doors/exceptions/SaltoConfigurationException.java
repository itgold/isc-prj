package com.iscweb.integration.doors.exceptions;

import com.iscweb.common.exception.BaseApplicationRuntimeException;

import java.net.SocketException;

/**
 * An error caused by Salto configuration.
 */
public class SaltoConfigurationException extends BaseApplicationRuntimeException {

    public SaltoConfigurationException(String msg, SocketException e) {
        super(msg, e);
    }
}
