package com.iscweb.common.log;

import org.springframework.boot.logging.LogLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Our components' accesses are logged automatically. In some cases, however, it is
 * required to change logging level for some of the components' accesses. So, this
 * annotation exists to set logging level for specific component methods.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentLogLevel {

    /**
     * What logging level should be used for the component.
     *
     * @return logging level.
     */
    LogLevel value();
}
