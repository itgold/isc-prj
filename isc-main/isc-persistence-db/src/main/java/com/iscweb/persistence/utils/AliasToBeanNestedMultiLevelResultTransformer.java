package com.iscweb.persistence.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.property.access.internal.PropertyAccessStrategyBasicImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyChainedImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyFieldImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyMapImpl;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * A transformer class for Hibernate results that transforms nested n-level entities from raw DB data.
 */
@Slf4j
public class AliasToBeanNestedMultiLevelResultTransformer extends AliasedTupleSubsetResultTransformer {

    private static final long serialVersionUID = -8047276133954328266L;

    private final Class<?> resultClass;
    private final Map<String, Class<?>> clazzMap = Maps.newHashMap();
    private final Map<String, Setter> settersMap = Maps.newHashMap();
    private boolean initialized;

    public AliasToBeanNestedMultiLevelResultTransformer(Class<?> resultClass) {
        this.resultClass = resultClass;
    }

    @Override
    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }

    private Object createAllMissingNestedObjects(Object rootObject, String alias, Map<String, Object> nestedObjectsMap)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object resultObject = null;
        int index = alias.lastIndexOf(".");
        while (index > 0) {
            String basePath = alias.substring(0, index);
            Object baseObject = nestedObjectsMap.get(basePath);
            if (baseObject == null) {
                baseObject = clazzMap.get(basePath).getDeclaredConstructor().newInstance();
                nestedObjectsMap.put(basePath, baseObject);
            }

            index = basePath.lastIndexOf(".");
            if (resultObject == null) {
                resultObject = baseObject;
            }
        }

        return resultObject != null ? resultObject : rootObject;
    }

    @Override
    public Object transformTuple(Object[] tuples, String[] aliases) {

        Map<String, Object> nestedObjectsMap = Maps.newHashMap();

        Object result;
        try {
            result = resultClass.getDeclaredConstructor().newInstance();
            String[] unresolvedAliases = aliases;
            if (initialized) {
                List<String> toBeResolved = Lists.newArrayList();
                List<String> resolvedAliases = Lists.newArrayList(settersMap.keySet());
                for (String alias : aliases) {
                    if (!resolvedAliases.contains(alias)) {
                        toBeResolved.add(alias);
                    }
                }
                unresolvedAliases = toBeResolved.toArray(new String[]{});
            }

            if (!initialized || unresolvedAliases.length > 0) {
                initialized = true;
                initialize(unresolvedAliases);
            }

            for (int a = 0; a < aliases.length; a++) {
                String alias = aliases[a];
                Object tuple = tuples[a];

                Object baseObject = createAllMissingNestedObjects(result, alias, nestedObjectsMap);
                Setter setter = settersMap.get(alias);
                if (setter != null) {
                    setter.set(baseObject, tuple, null);
                } else {
                    log.warn("Unable to resolve setter for: " + alias);
                }
            }

            for (Map.Entry<String, Object> entry : nestedObjectsMap.entrySet()) {
                Setter setter = settersMap.get(entry.getKey());
                if (entry.getKey().contains(".")) {
                    int index = entry.getKey().lastIndexOf(".");
                    String basePath = entry.getKey().substring(0, index);
                    Object obj = nestedObjectsMap.get(basePath);
                    setter.set(obj, entry.getValue(), null);
                } else {
                    setter.set(result, entry.getValue(), null);
                }
            }

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new HibernateException("Could not instantiate result class: " + resultClass.getName(), e);
        }

        return result;
    }


    private void initialize(String[] aliases) {
        final PropertyAccessStrategyChainedImpl propertyAccessorStrategy = new PropertyAccessStrategyChainedImpl(
                PropertyAccessStrategyFieldImpl.INSTANCE,
                PropertyAccessStrategyMapImpl.INSTANCE,
                PropertyAccessStrategyBasicImpl.INSTANCE
        );

        for (String alias: aliases) {
            Class<?> baseClass = resultClass;

            if (alias.contains(".")) {
                String[] split = alias.split("\\.");
                StringBuilder res = new StringBuilder();

                for (int i = 0; i < split.length; i++) {
                    if (res.length() > 0) {
                        res.append(".");
                    }

                    String item = split[i];
                    res.append(item);
                    String resString = res.toString();

                    PropertyAccess propertyAccessor = propertyAccessorStrategy.buildPropertyAccess(baseClass, item);

                    if (i == split.length - 1) {
                        clazzMap.put(resString, baseClass);
                        settersMap.put(resString, propertyAccessor.getSetter());
                        break;
                    }

                    Class<?> clazz = clazzMap.get(resString);
                    if (clazz == null) {
                        clazz = propertyAccessor.getGetter().getReturnType();
                        settersMap.put(resString, propertyAccessor.getSetter());
                        clazzMap.put(resString, clazz);
                    }
                    baseClass = clazz;
                }
            } else {
                clazzMap.put(alias, resultClass);
                settersMap.put(alias, propertyAccessorStrategy.buildPropertyAccess(resultClass, alias).getSetter());
            }
        }
    }
}
