package com.iscweb.component.web.auth.jwt.exceptions;

import com.iscweb.common.exception.BaseApplicationRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Username or password is incorrect")
public class UnauthorizedException extends BaseApplicationRuntimeException {
}
