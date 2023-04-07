package com.iscweb.integration.doors.exceptions;

import com.iscweb.integration.doors.models.SaltoErrorDto;
import com.iscweb.common.sis.exceptions.SisBusinessException;
import lombok.Getter;

/**
 * A generic Salto request error.
 */
public class SaltoRequestException extends SisBusinessException {

    @Getter
    private final SaltoErrorDto error;

    public SaltoRequestException(final SaltoErrorDto error) {
        super(error.getMessage());
        this.error = error;
    }
}
