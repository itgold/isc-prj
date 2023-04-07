package com.iscweb.app.main.util;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Fake authentication provider that is used as a placeholder in case of real authentication
 * provider is not available.
 * User: skurenkov
 */
public final class NullAuthenticationProvider implements AuthenticationProvider {

    /**
     * @see AuthenticationProvider#authenticate(Authentication)
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    /**
     * @see AuthenticationProvider#supports(Class)
     */
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
