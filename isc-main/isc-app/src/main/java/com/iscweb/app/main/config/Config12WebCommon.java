package com.iscweb.app.main.config;

import com.iscweb.app.main.util.MinimalInterceptor;
import com.iscweb.component.web.util.RequestMetadataInterceptor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurerAdapter to perform configuration that is common to both WEB and API profiles.
 */
@Order(12)
@Configuration
@Profile("web | api")
public class Config12WebCommon implements WebMvcConfigurer {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RequestMetadataInterceptor requestMetadataInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getRequestMetadataInterceptor());
        // registry.addInterceptor(new MinimalInterceptor()).addPathPatterns("/**");
    }
}


