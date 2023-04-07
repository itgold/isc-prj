package com.iscweb.common.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marker annotation to provide information for calculation of hierarchical event path.
 */
@Retention(RUNTIME)
@Target({TYPE})
public @interface EventPath {
    /**
     * Relative Event path.
     *
     * Note: Absolute path for an event/alarm will be calculated for the specific event instance or event class
     * based on all classes it extends up to the one marked with @EventPath annotation with <code>root</code> property set to true.
     *
     * @return Relative Event path
     */
    String path();

    /**
     * Mark if the class is the top in events/alarms classes hierarchy.
     * @return <code>true</code> if the class is top level class in event classes hierarchy
     */
    boolean root() default false;
}
