package com.iscweb.common.util;

import com.google.common.collect.Sets;
import com.iscweb.common.model.dto.ITrackableDto;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Dynamic proxy implementation to track field modifications in DTO objects.
 * The only requirement for DTO object is to implement <code>ITrackableDto</code> interface.
 *
 * Note: In that case we are "proxying" or "decorating" implementation class so we cannot use JDK dynamic
 * proxies for that purpose. We are depend on Javassist for that reason.
 *
 * @param <T> Target type to decorate with change tracking logic.
 * @see <a href="https://www.javassist.org/">https://www.javassist.org/</a>
 */
@Slf4j
public class TrackableDtoProxy<T extends ITrackableDto> implements MethodHandler {

    private static final String METHOD_MODIFIED = "isModified";
    private static final String METHOD_SET = "set";

    private final T obj;
    private final Set<String> modifiedFields;

    private TrackableDtoProxy(final T obj, Collection<String> modifiedFields) {
        this.obj = obj;
        this.modifiedFields = Sets.newHashSet(modifiedFields);
    }

    /**
     * Decorate DTO object to allow tracking.
     *
     * @param obj DTO object to decorate
     * @param <T> DTO object type
     * @return new proxied object instance
     */
    public static <T extends ITrackableDto> T getProxy(final T obj) {
        return TrackableDtoProxy.getProxy(obj, Collections.emptyList());
    }

    /**
     * Decorate DTO object to allow tracking.
     *
     * @param obj DTO object to decorate
     * @param modifiedFields List of already modified fields we know about before start tracking changes.
     * @param <T> DTO object type
     * @return new proxied object instance
     */
    @SuppressWarnings("unchecked")
    public static <T extends ITrackableDto> T getProxy(final T obj, List<String> modifiedFields) {
        ProxyFactory factory = new ProxyFactory();
        factory.setSuperclass(obj.getClass());

        T result;
        if (obj instanceof TrackableDtoProxy) {
            result = obj;
        } else {
            try {
                result = (T) factory.create(new Class<?>[0], new Object[0], new TrackableDtoProxy<>(obj, modifiedFields));
            } catch (Exception e) {
                result = obj;
            }
        }

        return result;
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        Object result;
        String methodName = thisMethod.getName();
        if (METHOD_MODIFIED.equalsIgnoreCase(methodName) && args.length == 1) {
            final String fieldName = String.valueOf(args[0]);
            result = modifiedFields.contains(fieldName);
        } else {
            if (methodName.startsWith(METHOD_SET)) {
                updateModifiedFlag(methodName);
            }
            result = thisMethod.invoke(obj, args);
        }

        return result;
    }

    private void updateModifiedFlag(String setterName) {
        String fieldName = setterName.substring(METHOD_SET.length());
        fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        modifiedFields.add(fieldName);
    }
}
