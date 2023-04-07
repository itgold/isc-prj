package com.iscweb.component.web.auth.jwt.exceptions;

import com.iscweb.common.exception.IApplicationException;
import com.iscweb.common.exception.util.ErrorCode;
import com.iscweb.component.web.auth.jwt.model.token.IJwtToken;
import org.springframework.security.core.AuthenticationException;

public class JwtExpiredTokenException extends AuthenticationException implements IApplicationException {

    private static final long serialVersionUID = -5959543783324224864L;

    private IJwtToken token;

    public JwtExpiredTokenException(String msg) {
        super(msg);
    }

    public JwtExpiredTokenException(IJwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ERROR_CODE_FORBIDDEN;
    }
}
