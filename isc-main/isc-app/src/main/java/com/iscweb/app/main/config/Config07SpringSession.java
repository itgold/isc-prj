package com.iscweb.app.main.config;

import com.google.common.collect.Maps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

@Order(7)
@Configuration
@Profile("web | api")
@EnableSpringHttpSession
public class Config07SpringSession {
    @Bean
    public SessionRepository getSessionRepository() {
        return new MapSessionRepository(Maps.newConcurrentMap());
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
