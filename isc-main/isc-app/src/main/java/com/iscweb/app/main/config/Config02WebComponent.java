package com.iscweb.app.main.config;

import com.iscweb.component.web.util.RequestMetadataInterceptor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
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
@ComponentScan(basePackages = {"com.iscweb.component.web"})
public class Config02WebComponent implements WebMvcConfigurer {

    /* Time, in days, to have the browser cache static resources. */
    private static final int TWENTY = 20;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RequestMetadataInterceptor requestMetadataInterceptor;

    /**
     * Log message with after successful configuration initialization.
     */
    @PostConstruct
    public void constructed() {
        log.info("█████████████████████████████ Web Component Enabled ████████████████████████████████");
    }

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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getRequestMetadataInterceptor());
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
        registry
                .addResourceHandler("/documentation/**").addResourceLocations("classpath:/META-INF/resources/");
    }

    /**
     * @see WebMvcConfigurer#addViewControllers(ViewControllerRegistry)
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.debug("Redirect view controllers configuration");
        registry.addRedirectViewController("/documentation/v2/api-docs", "/v2/api-docs");
        registry.addRedirectViewController("/documentation/configuration/ui", "/configuration/ui");
        registry.addRedirectViewController("/documentation/configuration/security", "/configuration/security");
        registry.addRedirectViewController("/documentation/swagger-resources", "/swagger-resources");
        registry.addRedirectViewController("/documentation", "/documentation/swagger-ui.html");
        registry.addRedirectViewController("/documentation/", "/documentation/swagger-ui.html");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable("default");
    }

    @Bean
    public MultipartResolver multipartResolver() {
        log.debug("MultipartResolver configuration");
        return new StandardServletMultipartResolver();
    }

    @Bean
    public HttpTraceRepository getHttpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }
}

