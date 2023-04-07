package com.iscweb.app.main.util;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;

/**
 * Yaml properties support for spring.
 * User: skurenkov
 * Date: 12/3/19
 */
public class YamlPropertyLoaderFactory extends DefaultPropertySourceFactory {

    /**
     * @see DefaultPropertySourceFactory#createPropertySource(String, EncodedResource)
     */
    @Override
    @SuppressWarnings("NullableProblems")
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        CompositePropertySource propertySource = new CompositePropertySource(name != null ? name : "default");
        new YamlPropertySourceLoader().load(resource.getResource().getFilename(),
                                            resource.getResource()).forEach(propertySource::addPropertySource);
        return propertySource;
    }
}
