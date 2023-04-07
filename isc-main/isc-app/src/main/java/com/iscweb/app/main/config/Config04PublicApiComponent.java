package com.iscweb.app.main.config;

import com.iscweb.component.api.util.EnumConverter;
import com.iscweb.component.web.util.ZonedDateTimeRangeHandlerMethodArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Order(4)
@Configuration
@Profile("web & api")
@ComponentScan(basePackages = {"com.iscweb.component.api"})
public class Config04PublicApiComponent implements WebMvcConfigurer {

    /**
     * Log message with after successful configuration initialization.
     */
    @PostConstruct
    public void constructed() {
        log.info("█████████████████████████████ Public API Component Enabled ████████████████████████████████");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver = new PageableHandlerMethodArgumentResolver();
        pageableHandlerMethodArgumentResolver.setFallbackPageable(PageRequest.of(0, 25));
        pageableHandlerMethodArgumentResolver.setPageParameterName("pageNumber");
        pageableHandlerMethodArgumentResolver.setSizeParameterName("pageSize");
        pageableHandlerMethodArgumentResolver.setMaxPageSize(50);

        argumentResolvers.add(pageableHandlerMethodArgumentResolver);
        argumentResolvers.add(new ZonedDateTimeRangeHandlerMethodArgumentResolver());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new EnumConverter());
    }

}

