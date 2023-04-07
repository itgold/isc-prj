package com.iscweb.simulator.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

/**
 * Configuration class for the Web module.
 * <p>
 * Web module is a module that exposes rest controllers and provides Phantasm client
 * to the end user for interacting with the application.
 * <p>
 * When Phantasm profile is enabled this configuration will load and configure
 * Phantasm controllers and enable application's Phantasm module.
 */
@Slf4j
@Order(2)
@Configuration
@Profile("web")
@ComponentScan(basePackages = {"com.iscweb.simulator.web"})
public class Config02SimulatorWebComponent implements WebMvcConfigurer {

    /* Time, in days, to have the browser cache static resources. */
    private static final int TWENTY = 20;

    /**
     * Total customization - see below for explanation.
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        log.debug("ContentNegotiationConfigurer configuration");
        configurer.favorPathExtension(true)
                  .favorParameter(false)
                  .ignoreAcceptHeader(true)
                  .useRegisteredExtensionsOnly(false)
                  .defaultContentType(MediaType.APPLICATION_JSON)
                  .mediaType("xml", MediaType.APPLICATION_XML)
                  .mediaType("json", MediaType.APPLICATION_JSON);
    }

    /**
     * Setting static resources cache control.
     *
     * @param registry resource handler registry.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.debug("Resource handlers configuration");
        //static resources mapping to classpath base location.
        registry.addResourceHandler("static/**")
                .addResourceLocations("classpath:static/")
                .setCacheControl(CacheControl.maxAge(TWENTY, TimeUnit.DAYS));
    }
}

