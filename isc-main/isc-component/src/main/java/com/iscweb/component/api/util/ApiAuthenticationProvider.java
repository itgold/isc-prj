package com.iscweb.component.api.util;

import com.iscweb.common.model.entity.IUser;
import com.iscweb.common.util.UserUtils;
import com.iscweb.service.security.IscPrincipal;
import com.iscweb.service.utils.LambdaTimer;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

/**
 * Authentication provider for OAuth scheme used by the public API.
 */
@Slf4j
@Component(value = "apiAuthenticationProvider")
public class ApiAuthenticationProvider implements AuthenticationProvider {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private @NonNull TokenStore tokenStore;

    /**
     * Attempt to authenticate the user.  If successful, find the IUser that corresponds to the given OAuth token
     * and return an Authentication object containing that IUser.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication result = null;
        String errorMessage = "OAuth2 authentication failed";

        if (authentication.isAuthenticated()) {
            log.warn("OAuth2 authentication already processed - {}", authentication);
        } else {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            if (!StringUtils.hasText(details.getTokenValue())) {
                errorMessage = "Empty oauth2 authentication";
                log.warn("Empty oauth2 authentication value has been received - {}", authentication);
            } else {
                OAuth2AccessToken oAuth2AccessToken = getTokenStore().readAccessToken(details.getTokenValue());
                if (oAuth2AccessToken == null || !StringUtils.hasText(oAuth2AccessToken.getValue())) {
                    errorMessage = "Invalid oauth2 access token";
                    log.warn("OAuth2 access token cannot be processed - {}", authentication);
                } else {
                    Instant now = ZonedDateTime.now().toInstant();
                    Date tokenExpiration = oAuth2AccessToken.getExpiration();

                    if (tokenExpiration == null || now.isAfter(tokenExpiration.toInstant())) {
                        LambdaTimer.init(() -> getTokenStore().removeAccessToken(oAuth2AccessToken)).schedule(60);
                        errorMessage = "Expired oauth2 access token";
                        log.warn("Expired OAuth2 access token - {}", authentication);
                    } else {
                        // Look up IUser by OAuth token.
                        // TODO(eric): Is there an issue forcing us to comment this out?
                        IUser user = null; //getOAuthService().findUserByToken(authentication.getName());
                        // Get list of user's authorities.
                        List<GrantedAuthority> authorities = UserUtils.grantedAuthoritiesFor(user);
                        // Create new authentication token.
                        result = new PreAuthenticatedAuthenticationToken(IscPrincipal.valueOf(user, authorities),
                                                                         authentication.getCredentials(),
                                                                         authorities);
                    }
                }
            }
        }

        // Instead of throwing a checked exception of the loop stack or an authentication exception, an OAuth2
        // exception will be handled gracefully and without generating a stack trace in the servlet call chain.
        if (result == null) {
            throw new OAuth2Exception(errorMessage);
        }
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
