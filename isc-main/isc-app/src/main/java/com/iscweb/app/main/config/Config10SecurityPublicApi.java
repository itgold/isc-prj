package com.iscweb.app.main.config;

import com.iscweb.service.api.OauthJdbcClientDetailsService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.sql.DataSource;

/**
 * This is a configuration for public api Authorization and Resource servers.
 *
 * @author skurenkov
 */
@Slf4j
@Order(10)
@Configuration
@Profile("api")
public class Config10SecurityPublicApi {

    /**
     * /**
     * OAuth token store.
     * At this point we use In-Memory store that doesn't survive server restart.
     *
     * @return new token store object instance.
     */
    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    /**
     * Authorization server configuration class.
     */
    @Configuration
    @EnableAuthorizationServer
    @Profile("api")
    public static class Config08AuthorizationServer extends AuthorizationServerConfigurerAdapter {

        @Getter
        @Setter
        private AuthenticationManager authenticationManager;

        @Getter
        @Setter(onMethod = @__({@Autowired}))
        private TokenStore tokenStore;

        @Getter
        @Setter(onMethod = @__({@Autowired}))
        private DataSource dataSource;

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
            oauthServer.tokenKeyAccess("permitAll()")
                       .checkTokenAccess("isAuthenticated()");
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

            log.info("Configuring oAuth authorization server");
            clients.withClientDetails(oauthClientDetailsService());
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
            endpoints.tokenStore(getTokenStore())
                     .authenticationManager(getAuthenticationManager());
        }

        @Bean
        public OauthJdbcClientDetailsService oauthClientDetailsService() {
            return new OauthJdbcClientDetailsService(getDataSource());
        }
    }

    /**
     * Resource server configuration class.
     */
    @Configuration
    @EnableResourceServer
    @Profile("api")
    public static class Config08ResourceServer extends ResourceServerConfigurerAdapter {

        @Getter
        @Setter
        private AuthenticationManager authenticationManager;

        @Getter
        @Setter(onMethod = @__({@Autowired}))
        private TokenStore tokenStore;

        @Override
        public void configure(HttpSecurity http) throws Exception {
            log.info("Configuring oAuth resource server");
            // @formatter:off
            http.
                requestMatchers()
                .antMatchers("/api/**")
            .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
            // @formatter:on
        }

        @Override
        @DependsOn("authenticationManager")
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.tokenStore(getTokenStore())
                     .authenticationManager(getAuthenticationManager());
        }
    }

}
