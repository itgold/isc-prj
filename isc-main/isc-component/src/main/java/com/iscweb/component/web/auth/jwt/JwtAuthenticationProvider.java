package com.iscweb.component.web.auth.jwt;

import com.iscweb.common.util.StringUtils;
import com.iscweb.component.web.auth.jwt.config.JwtSettings;
import com.iscweb.component.web.auth.jwt.model.token.IJwtToken;
import com.iscweb.component.web.auth.jwt.model.token.JwtAuthenticationToken;
import com.iscweb.component.web.auth.jwt.model.token.RawAccessJwtToken;
import com.iscweb.service.security.IscPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An {@link AuthenticationProvider} implementation that uses the provided {@link IJwtToken}
 * to perform authentication.
 */
@Slf4j
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private JwtSettings jwtSettings;

    /**
     * @see AuthenticationProvider#authenticate(Authentication)
     */
    @Override
    @SuppressWarnings("unchecked")
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();

        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(getJwtSettings().getTokenSigningKey());
        String username = jwsClaims.getBody().getSubject();
        Long userId = safeLong(jwsClaims.getBody().getAudience(), null);
        List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
        List<GrantedAuthority> authorities = scopes.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Why are we loading user details for each request?
        // IscPrincipal user = (IscPrincipal) userService.loadUserByUsername(username);
        IscPrincipal user = new IscPrincipal(username, StringUtils.EMPTY, userId, authorities);
        return new JwtAuthenticationToken(user, authorities, rawAccessToken);
    }

    /**
     * @see AuthenticationProvider#supports(Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private Long safeLong(String value, Long defaultValue) {
        Long result = defaultValue;
        if (!StringUtils.isBlank(value)) {
            try {
                result = Long.valueOf(value);
            } catch (Exception e) {
                log.error("Invalid user id in the JWT token: {}", value);
            }
        }

        return result;
    }
}
