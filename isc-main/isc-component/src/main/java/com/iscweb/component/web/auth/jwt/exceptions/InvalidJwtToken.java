package com.iscweb.component.web.auth.jwt.exceptions;

import com.iscweb.common.exception.IApplicationException;
import com.iscweb.common.exception.util.ErrorCode;

// TODO(eric): If this is an IApplicationException that extends RuntimeException, why does it not just
//  extend BaseApplicationRuntimeException?
public class InvalidJwtToken extends RuntimeException implements IApplicationException {

    private static final long serialVersionUID = -294671188037098603L;

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ERROR_CODE_UNAUTHORIZED;
    }
}
