package com.iscweb.component.web.util;

import com.iscweb.component.web.auth.SecurityConstants;
import com.iscweb.service.security.IscPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Application security utils.
 */
@Slf4j
public final class WebSecurityUtils {

    /**
     * Extracts JWT session token from the request object.
     *
     * @param request http servlet request instance.
     * @return a string value of a token.
     */
    public static String extractSessionToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null ? (String) session.getAttribute(SecurityConstants.AUTHENTICATION_COOKIE_NAME) : null;
    }

    /**
     * Checks if security context is currently initialized.
     * @param request HTTP servlet request.
     * @return true if HTTP session has information regarding the security context.
     */
    public static boolean isSecurityContextInitialized(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY) != null;
    }

    /**
     * Used to initialize session with the security context and preserve JWT token as a session attribute.
     *
     * @param userPrincipal  currently logged in user principal.
     * @param request        http servlet request object.
     * @param token          String representation of jtw token.
     * @param replaceSession is current session needs to be invalidated and created a new one?
     */
    public static void updateSecurityContext(final IscPrincipal userPrincipal, HttpServletRequest request, String token, boolean replaceSession) {

        List<GrantedAuthority> authorities = userPrincipal.getAuthorities()
                                                        .stream()
                                                        .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                                                        .collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);

        try {
            SecurityContext ctx = SecurityContextHolder.getContext();
            if (ctx == null) {
                ctx = SecurityContextHolder.createEmptyContext();
            }
            ctx.setAuthentication(authentication);
            SecurityContextHolder.setContext(ctx);

            HttpSession session = request.getSession(false);
            if (session != null) {
                if (replaceSession && !session.isNew()) {
                    session.invalidate();
                    session = request.getSession(true);
                }
            } else {
                session = request.getSession(true);
            }
            session.setAttribute(SecurityConstants.AUTHENTICATION_COOKIE_NAME, token);
        } catch (Exception e) {
            // Inn case of any known or unknown errors we should clear up security context.
            log.error("Unable to update security context", e);
            SecurityContextHolder.clearContext();
        }
    }
}
