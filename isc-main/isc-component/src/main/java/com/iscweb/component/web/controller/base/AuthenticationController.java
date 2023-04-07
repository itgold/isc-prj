package com.iscweb.component.web.controller.base;

import com.iscweb.component.web.auth.SecurityConstants;
import com.iscweb.component.web.auth.jwt.config.JwtSettings;
import com.iscweb.component.web.auth.jwt.exceptions.ForbiddenException;
import com.iscweb.component.web.auth.jwt.exceptions.InvalidJwtToken;
import com.iscweb.component.web.auth.jwt.exceptions.UnauthorizedException;
import com.iscweb.component.web.auth.jwt.extractor.ITokenExtractor;
import com.iscweb.component.web.auth.jwt.model.JwtTokenResponseDto;
import com.iscweb.component.web.auth.jwt.model.LoginRequestDto;
import com.iscweb.component.web.auth.jwt.model.token.IJwtToken;
import com.iscweb.component.web.auth.jwt.model.token.JwtTokenFactory;
import com.iscweb.component.web.auth.jwt.model.token.RawAccessJwtToken;
import com.iscweb.component.web.auth.jwt.model.token.RefreshToken;
import com.iscweb.component.web.auth.jwt.verifier.ITokenVerifier;
import com.iscweb.component.web.controller.IApplicationController;
import com.iscweb.component.web.util.WebSecurityUtils;
import com.iscweb.service.security.IscPrincipal;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * A controller that is used to authenticate users based on JWT token.
 */
@Slf4j
@RestController
public class AuthenticationController implements IApplicationController {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private PasswordEncoder encoder;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private UserDetailsService userService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private JwtSettings jwtSettings;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private JwtTokenFactory tokenFactory;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ITokenExtractor tokenExtractor;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ITokenVerifier tokenVerifier;

    /**
     * Logs user in with the given JWT token.
     *
     * @param loginRequest login request data.
     * @param request http servlet request instance.
     * @return jwt token response dto object.
     */
    @PostMapping(SecurityConstants.AUTHENTICATION_URL)
    public JwtTokenResponseDto login(@RequestBody LoginRequestDto loginRequest, HttpServletRequest request) {

        if (StringUtils.isBlank(loginRequest.getUsername()) || StringUtils.isBlank(loginRequest.getPassword())) {
            throw new UnauthorizedException();
        }

        IscPrincipal userContext;
        try {
            userContext = (IscPrincipal) getUserService().loadUserByUsername(loginRequest.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new UnauthorizedException();
        }

        if (userContext == null) {
            throw new UnauthorizedException();
        }

        if (!getEncoder().matches(loginRequest.getPassword(), userContext.getPassword())) {
            throw new UnauthorizedException();
        }

        if (CollectionUtils.isEmpty(userContext.getAuthorities())) {
            throw new ForbiddenException();
        }

        IJwtToken accessToken = getTokenFactory().createAccessToken(userContext);
        IJwtToken refreshToken = getTokenFactory().createRefreshToken(userContext);

        JwtTokenResponseDto result = buildTokenResponseDto(userContext, accessToken, refreshToken);

        WebSecurityUtils.updateSecurityContext(userContext, request, accessToken.getToken(), true);

        return result;
    }

    /**
     * Refreshes the given JWT token.
     *
     * @param request http servlet request instance.
     * @return jwt token response dto.
     */
    @GetMapping(value = SecurityConstants.REFRESH_TOKEN_URL, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody JwtTokenResponseDto refreshToken(HttpServletRequest request) {
        String tokenPayload = getTokenExtractor().extract(
                request.getHeader(SecurityConstants.AUTHENTICATION_HEADER_NAME)
        );

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, getJwtSettings().getTokenSigningKey())
                                                .orElseThrow(InvalidJwtToken::new);

        String jti = refreshToken.getJti();
        if (!getTokenVerifier().verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
        IscPrincipal userContext = (IscPrincipal) getUserService().loadUserByUsername(subject);
        if (userContext == null) {
            throw new UsernameNotFoundException("User not found: " + subject);
        }

        if (CollectionUtils.isEmpty(userContext.getAuthorities())) {
            throw new InsufficientAuthenticationException("User has no roles assigned");
        }

        IJwtToken accessToken = getTokenFactory().createAccessToken(userContext);
        JwtTokenResponseDto result = buildTokenResponseDto(userContext, accessToken, refreshToken);

        WebSecurityUtils.updateSecurityContext(userContext, request, accessToken.getToken(), false);

        return result;
    }

    /**
     * Used to build JWT token response DTO based on given parameters.
     *
     * @param userContext current user context.
     * @param accessToken jwt access token.
     * @param refreshToken  jwt refresh token.
     * @return new instance of initialized token response dto.
     */
    private JwtTokenResponseDto buildTokenResponseDto(IscPrincipal userContext, IJwtToken accessToken, IJwtToken refreshToken) {
        JwtTokenResponseDto result = new JwtTokenResponseDto();
        result.setUsername(userContext.getUsername());
        result.setAccessToken(accessToken.getToken());
        result.setExpiration(jwtSettings.getTokenExpirationTime() * 60);
        result.setRefreshToken(refreshToken.getToken());
        result.setRoles(userContext.getAuthorities()
                                   .stream()
                                   .map(GrantedAuthority::getAuthority)
                                   .collect(Collectors.toList()));
        return result;
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
