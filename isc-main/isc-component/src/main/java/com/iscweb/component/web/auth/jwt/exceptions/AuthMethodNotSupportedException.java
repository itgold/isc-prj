package com.iscweb.component.web.auth.jwt.exceptions;

import com.iscweb.common.exception.IApplicationException;
import com.iscweb.common.exception.util.ErrorCode;
import org.springframework.security.authentication.AuthenticationServiceException;

public class AuthMethodNotSupportedException extends AuthenticationServiceException implements IApplicationException {

    private static final long serialVersionUID = 3705043083010304496L;

    public AuthMethodNotSupportedException(String msg) {
        super(msg);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ERROR_CODE_BAD_REQUEST;
    }
}
