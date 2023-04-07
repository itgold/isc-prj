package com.iscweb.common.util;

import com.google.common.collect.Lists;
import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.event.IEvent;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class EventUtils {

    private static final String PATH_SEPARATOR = ".";
    public static final String PATH_WILDCARD = "*";
    private static final String TOP_CLASS_NAME = Object.class.getSimpleName();

    private static final String SYMBOL_WILDCARD = "\\*";
    private static final String SYMBOL_WILDCARD_RE = ".*";
    private static final String SYMBOL_PERIOD = "\\.";
    private static final String SYMBOL_PERIOD_RE = "\\\\.";

    public static String generatePath(String ... paths) {
        List<String> values = Arrays.stream(paths).filter(Objects::nonNull).collect(Collectors.toList());
        Collections.reverse(values);
        return StringUtils.collectionToDelimitedString(values, PATH_SEPARATOR);
    }

    public static String eventPath(Class<? extends IEvent> baseClass, EntityType entityType, String eventType) {
        return EventUtils.eventPath(baseClass) + PATH_SEPARATOR
                + (entityType != null ? entityType.name().toLowerCase() : PATH_WILDCARD)
                + (eventType != null ? PATH_SEPARATOR + eventType : com.iscweb.common.util.StringUtils.EMPTY);
    }

    public static String eventPath(Object event) {
        Class<?> obj = event instanceof Class ? (Class<?>) event : event.getClass();
        EventPath eventPath = obj.getAnnotation(EventPath.class);
        return eventPath != null ? eventPath.path() : null;
    }

    public static String generateEventPath(Object event) {
        List<String> paths = Lists.newArrayList();

        Class<?> obj = event instanceof Class ? (Class<?>) event : event.getClass();
        while (obj != null && !obj.getSimpleName().equals(TOP_CLASS_NAME)) {

            EventPath eventPath = obj.getAnnotation(EventPath.class);
            String path = eventPath != null ? eventPath.path() : null;
            paths.add(path != null ? path : PATH_WILDCARD);

            if (eventPath != null && eventPath.root()) {
                break;
            }

            obj = obj.getSuperclass();
        }

        String path = generatePath(paths.toArray(new String[]{}));
        return path.endsWith(PATH_WILDCARD) ? path : path + PATH_WILDCARD;
    }

    public static Pattern eventPathPattern(String eventPath) {
        String sanitizedPattern = eventPath.replaceAll(SYMBOL_PERIOD, SYMBOL_PERIOD_RE);
        sanitizedPattern = sanitizedPattern.replaceAll(SYMBOL_WILDCARD, SYMBOL_WILDCARD_RE);
        return Pattern.compile(sanitizedPattern);
    }
}
