package com.iscweb.component.web.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iscweb.common.model.dto.response.StringResponseDto;
import com.iscweb.common.util.StringUtils;
import com.iscweb.component.web.auth.SecurityConstants;
import com.iscweb.component.web.auth.jwt.exceptions.AuthMethodNotSupportedException;
import com.iscweb.component.web.auth.jwt.exceptions.JwtExpiredTokenException;
import com.iscweb.component.web.auth.jwt.extractor.ITokenExtractor;
import com.iscweb.component.web.auth.jwt.model.token.JwtAuthenticationToken;
import com.iscweb.component.web.auth.jwt.model.token.RawAccessJwtToken;
import com.iscweb.component.web.util.WebSecurityUtils;
import com.iscweb.service.security.IscPrincipal;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Validates the provided JWT Token.
 */
@Slf4j
public class JwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final ThreadLocal<Exception> SPRING_SECURITY_LAST_EXCEPTION = new ThreadLocal<>();

    @Getter
    @Setter
    private ObjectMapper mapper;

    @Getter
    @Setter
    private ITokenExtractor tokenExtractor;

    public JwtTokenAuthenticationProcessingFilter(ObjectMapper mapper,
                                                  ITokenExtractor tokenExtractor,
                                                  RequestMatcher matcher) {
        super(matcher);
        setMapper(mapper);
        setTokenExtractor(tokenExtractor);
    }

    public static Exception lastAuthenticationException() {
        return SPRING_SECURITY_LAST_EXCEPTION.get();
    }

    /**
     * @see AbstractAuthenticationProcessingFilter#requiresAuthentication(HttpServletRequest, HttpServletResponse)
     */
    protected boolean requiresAuthentication(HttpServletRequest request,
                                             HttpServletResponse response) {
        boolean requiredAuth = super.requiresAuthentication(request, response);
        if (requiredAuth && WebSecurityUtils.isSecurityContextInitialized(request)) {
            String tokenPayload = request.getHeader(SecurityConstants.AUTHENTICATION_HEADER_NAME);
            requiredAuth = !StringUtils.isBlank(tokenPayload);
        }

        return requiredAuth;
    }

    /**
     * @see AbstractAuthenticationProcessingFilter#attemptAuthentication(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication result;

        String tokenPayload = request.getHeader(SecurityConstants.AUTHENTICATION_HEADER_NAME);
        if (StringUtils.isBlank(tokenPayload)) {
            // We accept the cookie and only allow one to access one resource with it:
            // SecurityConstants.SECURED_BASE_URL
            final String url = request.getRequestURI();
            if (url.startsWith(SecurityConstants.SECURED_BASE_URL)) {
                tokenPayload = WebSecurityUtils.extractSessionToken(request);
            }
        }
        logger.debug("Attempt to authenticate: " + tokenPayload + ", session: " + request.getSession(false));
        RawAccessJwtToken token = new RawAccessJwtToken(getTokenExtractor().extract(tokenPayload));
        result = getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));

        return result;
    }

    /**
     * @see AbstractAuthenticationProcessingFilter#successfulAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, Authentication)
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

//        SecurityContext context = SecurityContextHolder.getContext();
//        if (null == context)
//            context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authResult);
//        SecurityContextHolder.setContext(context);

        JwtAuthenticationToken token = (JwtAuthenticationToken) authResult;
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authResult.getCredentials();
        WebSecurityUtils.updateSecurityContext((IscPrincipal) token.getPrincipal(), request, rawAccessToken.getToken(), false);

        chain.doFilter(request, response);
    }

    /**
     * @see AbstractAuthenticationProcessingFilter#unsuccessfulAuthentication(HttpServletRequest, HttpServletResponse, AuthenticationException)
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException e) throws IOException {

        SPRING_SECURITY_LAST_EXCEPTION.set(e);
        SecurityContextHolder.clearContext();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String username = request.getParameter("username");
        if (username != null) {
            log.warn("User {} login failure", username);
        }

//        TODO(eric): Do we want to put this back?
//        HttpSession session = request.getSession(false);
//        if (null != session && !session.isNew()) {
//            session.invalidate(); // kill the current session
//        }

        final String url = request.getRequestURI();
        if (!url.startsWith(SecurityConstants.API_REST_URL)) {
            response.sendRedirect(SecurityConstants.PUBLIC_BASE_URL);
        } else {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            if (e instanceof BadCredentialsException) {
                getMapper().writeValue(response.getWriter(),
                        StringResponseDto.of(SecurityConstants.SecurityErrorCode.AUTHENTICATION, "Invalid username or password"));
            } else if (e instanceof JwtExpiredTokenException) {
                getMapper().writeValue(response.getWriter(),
                        StringResponseDto.of(SecurityConstants.SecurityErrorCode.JWT_TOKEN_EXPIRED, "Token has expired"));
            } else if (e instanceof AuthMethodNotSupportedException) {
                getMapper().writeValue(response.getWriter(),
                        StringResponseDto.of(SecurityConstants.SecurityErrorCode.AUTHENTICATION, e.getMessage()));
            } else {
                getMapper().writeValue(response.getWriter(),
                        StringResponseDto.of(SecurityConstants.SecurityErrorCode.AUTHENTICATION, "Authentication failed"));
            }
        }
    }
}
