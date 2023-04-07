package com.iscweb.simulator.config;

import com.iscweb.common.security.ApplicationSecurity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
public class Config09SimulatorSecurityWeb extends WebSecurityConfigurerAdapter {

    private static final String[] SKIP_SECURITY_ENDPOINTS = new String[] {
            "/**",
            };

    private static final String[] PERMIT_ALL_ENDPOINTS = new String[] {
            "/",
            "/**",
            };

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
           .formLogin().disable()
                .httpBasic().disable()
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
            ).permitAll();

        // @formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

