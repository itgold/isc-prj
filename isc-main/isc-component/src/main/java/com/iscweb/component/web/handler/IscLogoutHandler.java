package com.iscweb.component.web.handler;

import com.iscweb.component.web.auth.jwt.config.JwtSettings;
import com.iscweb.component.web.auth.jwt.extractor.ITokenExtractor;
import com.iscweb.component.web.auth.jwt.model.token.RawAccessJwtToken;
import com.iscweb.component.web.util.WebSecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * Loop logout action handler.
 *
 * @author skurenkov
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IscLogoutHandler extends SecurityContextLogoutHandler {

    @Getter
    private @NonNull ITokenExtractor tokenExtractor;

    @Getter
    private @NonNull JwtSettings jwtSettings;

    /**
     * @see super#logout(HttpServletRequest, HttpServletResponse, Authentication)
     */
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String accessToken = WebSecurityUtils.extractSessionToken(request);
        String sessionUser = getSessionUser(accessToken);
        HttpSession session = request.getSession(false);
        if (sessionUser != null) {
            long sessionLength = session != null ? TimeUnit.MILLISECONDS.toSeconds(session.getLastAccessedTime() - session.getCreationTime()) : 0L;
            log.debug("logout.event.user={}, session.length={}", sessionUser, sessionLength);
        } else {
            log.warn("logout.event=error, failed to get the user from the security context");
        }
    }

    private String getSessionUser(String accessToken) {
        String sessionUser = null;
        if (accessToken != null) {
            try {
                RawAccessJwtToken token = new RawAccessJwtToken(tokenExtractor.extract(accessToken));
                Jws<Claims> jwsClaims = token.parseClaims(jwtSettings.getTokenSigningKey());
                sessionUser = jwsClaims.getBody().getSubject();
            } catch (Exception e) {
                // just ignore
            }
        }

        return sessionUser;
    }
}
