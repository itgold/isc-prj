package com.iscweb.component.web.auth.jwt.model.token;

import com.iscweb.service.security.IscPrincipal;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * An {@link org.springframework.security.core.Authentication} implementation
 * that is designed for simple presentation of JwtToken.
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 2877954820905567501L;

    @Getter
    private RawAccessJwtToken rawAccessToken;

    @Getter
    private IscPrincipal userContext;

    public JwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(null);
        this.rawAccessToken = unsafeToken;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(IscPrincipal userContext,
                                  Collection<? extends GrantedAuthority> authorities,
                                  RawAccessJwtToken rawAccessToken) {
        super(authorities);
        this.eraseCredentials();
        this.userContext = userContext;
        this.rawAccessToken = rawAccessToken;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return getRawAccessToken();
    }

    @Override
    public Object getPrincipal() {
        return getUserContext();
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
