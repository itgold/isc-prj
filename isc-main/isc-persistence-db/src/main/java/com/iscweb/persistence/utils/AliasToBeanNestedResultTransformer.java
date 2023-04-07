package com.iscweb.persistence.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.hibernate.HibernateException;
import org.hibernate.property.access.internal.PropertyAccessStrategyBasicImpl;
import org.hibernate.property.access.spi.PropertyAccess;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;
import org.hibernate.transform.ResultTransformer;

import java.util.List;
import java.util.Map;

/**
 * Helper transformer for results from Hibernate that handles nested entities.
 * Note: please use <code>AliasToBeanNestedMultiLevelResultTransformer</code> instead.
 * I left this here in case the main transformer did not work.
 */
public class AliasToBeanNestedResultTransformer extends AliasedTupleSubsetResultTransformer {

    private static final long serialVersionUID = -8047276133980128266L;

    private final Class<?> resultClass;

    public AliasToBeanNestedResultTransformer(Class<?> resultClass) {
        this.resultClass = resultClass;
    }

    @Override
    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object transformTuple(Object[] tuples, String[] aliases) {

        Map<Class<?>, List<?>> subclassToAlias = Maps.newHashMap();
        List<String> nestedAliases = Lists.newArrayList();

        try {
            for (int i = 0; i < aliases.length; i++) {
                String alias = aliases[i];
                Object tuple = tuples[i];

                if (alias.contains(".")) {
                    nestedAliases.add(alias);

                    String[] sp = alias.split("\\.");
                    String fieldName = sp[0];
                    String aliasName = sp[1];

                    Class<?> subclass = resultClass.getDeclaredField(fieldName).getType();

                    if (!subclassToAlias.containsKey(subclass)) {
                        List<Object> list = Lists.newArrayList();
                        list.add(Lists.newArrayList());
                        list.add(Lists.newArrayList());
                        list.add(fieldName);
                        subclassToAlias.put(subclass, list);
                    }
                    ((List<Object>) subclassToAlias.get(subclass).get(0)).add(tuple);
                    ((List<String>) subclassToAlias.get(subclass).get(1)).add(aliasName);
                }
            }
        } catch (NoSuchFieldException e) {
            throw new HibernateException("Could not instantiate result class: " + resultClass.getName());
        }

        Object[] newTuple = new Object[aliases.length - nestedAliases.size()];
        String[] newAliases = new String[aliases.length - nestedAliases.size()];
        int i = 0;
        for (int j = 0; j < aliases.length; j++) {
            if (!nestedAliases.contains(aliases[j])) {
                newTuple[i] = tuples[j];
                newAliases[i] = aliases[j];
                ++i;
            }
        }

        ResultTransformer rootTransformer = new AliasToBeanResultTransformer(resultClass);
        Object root = rootTransformer.transformTuple(newTuple, newAliases);

        for (Class<?> subclass : subclassToAlias.keySet()) {
            ResultTransformer subclassTransformer = new AliasToBeanResultTransformer(subclass);
            @SuppressWarnings("SuspiciousToArrayCall") // hibernate api usage invocation
            Object subObject = subclassTransformer.transformTuple(
                    ((List<Object>) subclassToAlias.get(subclass).get(0)).toArray(),
                    ((List<Object>) subclassToAlias.get(subclass).get(1)).toArray(new String[0])
                                                                                                            );

            PropertyAccess propertyAccess = PropertyAccessStrategyBasicImpl.INSTANCE.buildPropertyAccess(resultClass,
                    (String) subclassToAlias.get(subclass).get(2));
            propertyAccess.getSetter().set(root, subObject, null);
        }

        return root;
    }
}
