package com.iscweb.component.web;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AjaxTimeoutRedirectFilter extends GenericFilterBean {

    public static final int DEFAULT_AJAX_SESSION_EXPIRED_CODE = 401;

    @Getter
    protected int ajaxExpirationCode = DEFAULT_AJAX_SESSION_EXPIRED_CODE;

    @Getter
    private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    @Getter
    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    /**
     * @see super#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException {
        try {
            chain.doFilter(request, response);
        } catch (IOException ioException) {
            log.debug("Chain was not processed normally. IO error: " + ioException.getMessage());
            throw ioException;
        } catch (Exception ex) {
            log.debug("Chain was not processed normally for URL: " + ((HttpServletRequest) request).getRequestURI() + " error: " + ex.getMessage());

            // See if this is due to call coming from AJAX which needs to be handled separately.
            Throwable[] causeChain = getThrowableAnalyzer().determineCauseChain(ex);
            RuntimeException authenticationException =
                    (AuthenticationException) getThrowableAnalyzer().getFirstThrowableOfType(AuthenticationException.class, causeChain);

            if (authenticationException == null) {
                authenticationException =
                        (AccessDeniedException) getThrowableAnalyzer().getFirstThrowableOfType(AccessDeniedException.class, causeChain);
            }

            if (authenticationException != null) {
                if (authenticationException instanceof AuthenticationException) {
                    throw authenticationException;
                } else if (authenticationException instanceof AccessDeniedException) {

                    if (getAuthenticationTrustResolver().isAnonymous(SecurityContextHolder.getContext()
                                                                                          .getAuthentication())) {
                        log.debug("User SESSION expired or not logged in yet");
                        String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");

                        if ("XMLHttpRequest".equals(ajaxHeader)) {
                            log.debug("Ajax call detected, send {} error code", getAjaxExpirationCode());
                            HttpServletResponse resp = (HttpServletResponse) response;
                            resp.sendError(getAjaxExpirationCode());
                        } else {
                            log.debug("Redirect to login page");
                            throw authenticationException;
                        }
                    } else {
                        throw authenticationException;
                    }
                }
            }
        }
    }

    private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
        /**
         * @see ThrowableAnalyzer#initExtractorMap()
         */
        @Override
        protected void initExtractorMap() {
            super.initExtractorMap();
            registerExtractor(ServletException.class, throwable -> {
                ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
                return ((ServletException) throwable).getRootCause();
            });
        }
    }
}
