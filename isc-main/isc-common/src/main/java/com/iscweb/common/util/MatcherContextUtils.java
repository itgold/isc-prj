package com.iscweb.common.util;

import com.iscweb.common.events.alerts.AlertMatchingContext;
import com.iscweb.common.model.alert.matcher.ItemClause;
import com.iscweb.common.model.alert.matcher.MatcherClause;
import com.iscweb.common.model.alert.matcher.Operator;
import com.iscweb.common.model.alert.matcher.types.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility helper class to process alert trigger matcher context comparisons
 */
@Slf4j
public class MatcherContextUtils {

    private static final String PROP_DATE = "dateTime.date";
    private static final String PROP_TIME = "dateTime.time";

    private static final String PROP_DEVICE_ID = "deviceId";
    private static final String PROP_EVENT = "event.";
    private static final String PROP_DEVICE = "device.";
    private static final String PROP_DEVICE_STATE = "deviceState.";

    private static final Map<Class<?>, ITypeComparator> COMPARATORS = new HashMap<>() {{
        put(Integer.class, new LongTypeComparator());
        put(Long.class, new LongTypeComparator());
        put(LocalDate.class, new DateTypeComparator());
        put(LocalTime.class, new TimeTypeComparator());
    }};

    @SuppressWarnings("unchecked")
    public static <T> T getPropertyValue(Object obj, String path) {
        BeanWrapper bean = new BeanWrapperImpl(obj);
        if (bean.getPropertyType(path) != null) {
            return (T) bean.getPropertyValue(path);
        }

        return null;
    }

    public static Object resolveValue(String path, AlertMatchingContext context) {
        // parse path "device.entityType" or "time" or "event.payload.type" etc.
        Object result = null;

        if (PROP_DEVICE_ID.equals(path)) { // 1. small improvement to access top level properties
            result = context.getDeviceId();
        } else if (PROP_DATE.equals(path)) {
            result = context.getDateTime().toLocalDate();
        } else if (PROP_TIME.equals(path)) {
            result = context.getDateTime().toLocalTime();
        } else if (path.startsWith(PROP_EVENT)) { // 2. use reflection
            result = getPropertyValue(context.getEvent(), path.substring(PROP_EVENT.length()));
        } else if (path.startsWith(PROP_DEVICE)) {
            // use reflection
            result = getPropertyValue(context.getDevice(), path.substring(PROP_DEVICE.length()));
        } else if (path.startsWith(PROP_DEVICE_STATE)) {
            // use reflection
            result = getPropertyValue(context.getDeviceState(), path.substring(PROP_DEVICE_STATE.length()));
        }

        return result;
    }

    public static boolean match(Object contextValue, Object value, Operator operator) {
        boolean result = false;

        if (contextValue == null && value == null) {
            result = true;
        }
        else if (contextValue != null && value != null) {
            Object contextValueFixed = contextValue;
            ITypeComparator comparator = COMPARATORS.get(contextValue.getClass());
            if (comparator == null) {
                comparator = new StringTypeComparator();
                contextValueFixed = String.valueOf(contextValue);
            }

            result = comparator.compare(contextValueFixed, value, operator);
        }

        return result;
    }

    public static boolean matchOne(MatcherClause matcher, AlertMatchingContext context) {
        for (ItemClause clause : matcher) {
            Object contextValue = MatcherContextUtils.resolveValue(clause.getProperty(), context);
            boolean isMatching = MatcherContextUtils.match(contextValue, clause.getValue(), clause.getOperator());
            if (isMatching) {
                return true; // no need to check other rules
            }
        }

        // check "or" first to match faster
        if (matcher.getOr() != null && MatcherContextUtils.matchOne(matcher.getOr(), context)) {
            return true;
        }
        return matcher.getAnd() != null && MatcherContextUtils.matchAll(matcher.getAnd(), context);
    }

    public static boolean matchAll(MatcherClause matcher, AlertMatchingContext context) {
        for (ItemClause clause : matcher) {
            Object contextValue = MatcherContextUtils.resolveValue(clause.getProperty(), context);
            boolean isMatching = MatcherContextUtils.match(contextValue, clause.getValue(), clause.getOperator());
            if (!isMatching) {
                return false; // no need to check other rules
            }
        }

        // check "and" first to fail faster
        if (matcher.getAnd() != null && !MatcherContextUtils.matchAll(matcher.getAnd(), context)) {
            return false;
        }
        return matcher.getOr() == null || MatcherContextUtils.matchOne(matcher.getOr(), context);
    }
}
