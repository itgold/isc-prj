package com.iscweb.service.event;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.service.IApplicationSecuredService;
import com.iscweb.common.util.EventUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Helper Event service to help to create dynamic specific Event instances.
 *
 * This is useful when you do not have upfront event class while compile time and want to create
 * correct class instance on runtime based on event path information.
 * For example, when reading serialized events from data storages like Elastic Search.
 *
 * This way, the created event objects will have correct specific types and will allow you to use instanceof or isAssignable
 * and safe class casting.
 */
@Slf4j
@Service
public class EventHelperService implements IApplicationSecuredService {

    @Getter
    private final Map<String, Pattern> patterns = Maps.newHashMap();
    @Getter
    private final Map<String, Class<?>> classes = Maps.newTreeMap(Collections.reverseOrder());

    @PostConstruct
    public void init() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(EventPath.class));

        List<BeanDefinition> definitions = Lists.newArrayList();
        for (BeanDefinition bd : scanner.findCandidateComponents("com.iscweb")) {
            definitions.add(bd);
        }

        stopwatch.stop();
        log.debug("Event classes lookup time: {} ms. Found events:", stopwatch.elapsed().toMillis());
        for (BeanDefinition bd: definitions) {
            try {
                Class<?> clazz = Class.forName(bd.getBeanClassName());
                String eventPath = EventUtils.generateEventPath(clazz);
                log.debug("   {} - Class: {}", eventPath, bd.getBeanClassName());
                getClasses().put(eventPath, clazz);
                getPatterns().put(eventPath, EventUtils.eventPathPattern(eventPath));
            } catch (ClassNotFoundException e) {
                log.error("Unable to retrieve event class info", e);
            }
        }
    }

    public <E extends BaseEvent<T>, T extends ITypedPayload> E createEventInstance(String eventPath)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        for (Map.Entry<String, Class<?>> entry : getClasses().entrySet()) {
            if (isMatching(eventPath, entry.getKey())) {
                Class<?> clazz = entry.getValue();
                return (E) clazz.getDeclaredConstructor().newInstance();
            }
        }

        throw new IllegalArgumentException("Unable to create instance of event for event path: " + eventPath);
    }

    protected boolean isMatching(String eventPath, String subscriptionPath) {
        return getPatterns()
                .get(subscriptionPath)
                .matcher(eventPath)
                .matches();
    }
}
