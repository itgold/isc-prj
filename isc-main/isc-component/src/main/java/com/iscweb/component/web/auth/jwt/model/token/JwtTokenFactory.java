package com.iscweb.component.web.auth.jwt.model.token;

import com.iscweb.common.util.StringUtils;
import com.iscweb.component.web.auth.jwt.config.JwtSettings;
import com.iscweb.component.web.auth.jwt.model.Scopes;
import com.iscweb.service.security.IscPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Factory class that should be used to create {@link IJwtToken}.
 */
@Component
public class JwtTokenFactory {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private JwtSettings settings;

    /**
     * Factory method for issuing new JWT Tokens.
     *
     * @param userContext user security context
     * @return access token
     */
    public AccessJwtToken createAccessToken(IscPrincipal userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) {
            throw new IllegalArgumentException("User doesn't have any privileges");
        }

        Claims claims = Jwts.claims()
                            .setAudience(String.valueOf(userContext.getUserId()))
                            .setSubject(userContext.getUsername());
        claims.put("scopes", userContext.getAuthorities()
                                        .stream()
                                        .map(Object::toString)
                                        .collect(Collectors.toList()));

        LocalDateTime currentTime = LocalDateTime.now();

        String token = Jwts.builder()
                           .setClaims(claims)
                           // .setId(UUID.randomUUID().toString())
                           .setIssuer(getSettings().getTokenIssuer())
                           .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                           .setExpiration(Date.from(currentTime
                                                            .plusMinutes(getSettings().getTokenExpirationTime())
                                                            .atZone(ZoneId.systemDefault()).toInstant()))
                           .signWith(SignatureAlgorithm.HS512, getSettings().getTokenSigningKey())
                           .compact();

        return new AccessJwtToken(token, claims);
    }

    public IJwtToken createRefreshToken(IscPrincipal userContext) {

        if (StringUtils.isBlank(userContext.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims()
                            .setAudience(String.valueOf(userContext.getUserId()))
                            .setSubject(userContext.getUsername());
        claims.put("scopes", Collections.singletonList(Scopes.REFRESH_TOKEN.authority()));

        String token = Jwts.builder()
                           .setClaims(claims)
                           .setIssuer(getSettings().getTokenIssuer())
                           // .setId(UUID.randomUUID().toString())
                           .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                           .setExpiration(Date.from(currentTime
                                                            .plusMinutes(getSettings().getRefreshTokenExpTime())
                                                            .atZone(ZoneId.systemDefault()).toInstant()))
                           .signWith(SignatureAlgorithm.HS512, getSettings().getTokenSigningKey())
                           .compact();

        return new AccessJwtToken(token, claims);
    }
}
