package com.iscweb.app.main.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iscweb.common.security.ApplicationSecurity;
import com.iscweb.component.web.auth.ApplicationAuthenticationEntryPoint;
import com.iscweb.component.web.auth.SecurityConstants;
import com.iscweb.component.web.auth.jwt.JwtAuthenticationProvider;
import com.iscweb.component.web.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.iscweb.component.web.auth.jwt.extractor.ITokenExtractor;
import com.iscweb.component.web.handler.IscLogoutHandler;
import com.iscweb.component.web.util.SkipPathRequestMatcher;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Primary configuration for the internal Web API.
 *
 * @author dmorozov
 */
@Slf4j
@Order(9)
@Configuration
@Profile("web")
@EnableWebSecurity
public class Config09SecurityWeb extends WebSecurityConfigurerAdapter {

    private static final String[] SKIP_SECURITY_ENDPOINTS = new String[] {
            "/static/**",
            "/packages/*",
            "/agreement*",
            "/favicon.ico",
            "/privacy-policy",
            "/error",
            "/actuator/**"
            };

    private static final String[] PERMIT_ALL_ENDPOINTS = new String[] {
            "/",
            "/login",
            "/ui/**",
            "/rest/status",
            "/rest/env",
            "/rest/password/update",
            "/rest/password/forgot",
            "/rest/password/send-reset/**",
            "/test/**", // todo(dmorozov): remove it when done with ES queries testing
            SecurityConstants.AUTHENTICATION_URL,
            SecurityConstants.REFRESH_TOKEN_URL,
            SecurityConstants.API_WS_ROOT_URL,
            };

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ApplicationAuthenticationEntryPoint authenticationErrorHandler;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ITokenExtractor tokenExtractor;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IscLogoutHandler logoutHandler;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ObjectMapper jsonMapper;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Getter
    @Value("${isc.dev.allowGraphQlClients:#{false}}")
    private Boolean allowGraphQlClients;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
           .antMatchers(HttpMethod.OPTIONS, "/**")
           // allow anonymous resource requests
           .antMatchers(SKIP_SECURITY_ENDPOINTS);
    }

    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .csrf()
                .ignoringAntMatchers(
                        Stream.of(
                                // todo(dmorozov): make sure all GraphQL requests are passing CSRF tokens
                                // getAllowGraphQlClients() ? Collections.singletonList("/rest/graphql**") : Collections.emptyList(),
                                Collections.singletonList("/rest/graphql**"),
                                Arrays.asList(PERMIT_ALL_ENDPOINTS)
                        ).flatMap(Collection::stream)
                                .collect(Collectors.toList())
                                .toArray(new String[] {})
                )
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(getAuthenticationErrorHandler())
            .and()
                .headers()
                    .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                    .frameOptions()
                        .sameOrigin()

            // create no session
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)

            .and()

            .anonymous()
                .principal(ApplicationSecurity.ANONYMOUS_USER)
                .authorities(ApplicationSecurity.ROLE_ANONYMOUS)

            .and()
                .authorizeRequests()

            .antMatchers(
                    PERMIT_ALL_ENDPOINTS
            ).permitAll()

            // Add separate authenticated for /api and /rest
            .antMatchers(
                    SecurityConstants.API_PUBLIC_ROOT_URL,
                    SecurityConstants.API_REST_ROOT_URL
            ).authenticated() // Protected API End-points

            // Only ADMINS hit the roles entry point
            .antMatchers("/rest/admin/**")
                .hasAnyAuthority(
                    ApplicationSecurity.ROLE_SYSTEM_ADMINISTRATOR,
                    ApplicationSecurity.ROLE_DISTRICT_ADMINISTRATOR
                )

            .anyRequest()
                .hasAuthority(ApplicationSecurity.ROLE_SYSTEM_ADMINISTRATOR)

            .and()
                // Logout configuration.
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(SecurityConstants.LOGOUT_URL))
                .addLogoutHandler(getLogoutHandler())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))
                .permitAll()

            .and()
            .apply(securityConfigurerAdapter(
                    Stream.of(
                            Arrays.asList(SKIP_SECURITY_ENDPOINTS),
                            Arrays.asList(PERMIT_ALL_ENDPOINTS)
                             ).flatMap(Collection::stream).collect(Collectors.toList())));
        // @formatter:on
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        // if (!(getApiAuthenticationProvider() instanceof NullAuthenticationProvider)) {
        //     auth.authenticationProvider(getApiAuthenticationProvider());
        // }
        auth.authenticationProvider(getJwtAuthenticationProvider());
        auth.authenticationEventPublisher(eventPublisher());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(List<String> pathsToSkip, String pattern) throws Exception {

        JwtTokenAuthenticationProcessingFilter result;

        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, pattern);
        result = new JwtTokenAuthenticationProcessingFilter(getJsonMapper(), getTokenExtractor(), matcher);
        result.setAuthenticationManager(authenticationManagerBean());

        return result;
    }

    private SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> securityConfigurerAdapter(List<String> permitAllList) {
        return new SecurityConfigurerAdapter<>() {
            @Override
            public void configure(HttpSecurity http) throws Exception {
                http.addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllList, SecurityConstants.API_REST_ROOT_URL), UsernamePasswordAuthenticationFilter.class);
            }
        };
    }

    private AuthenticationEventPublisher eventPublisher() {
        return new AuthenticationEventPublisher() {
            @Override
            public void publishAuthenticationSuccess(Authentication authentication) {
                log.debug("Successful authentication for {}", authentication);
            }

            @Override
            public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
                log.debug("Failed authentication for {}", authentication);
            }
        };
    }
}

