package com.iscweb.component.web.auth.jwt.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Raw representation of JWT Token.
 */
@AllArgsConstructor
public final class AccessJwtToken implements IJwtToken {

    @Getter
    private final String token;

    @Getter
    @JsonIgnore
    private Claims claims;
}
