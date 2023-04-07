package com.iscweb.common.model.alert.matcher.types;

import com.iscweb.common.model.alert.matcher.Operator;

/**
 * Interface defines helper utility class to help with value comparison when the type is unknown upfront.
 */
public interface ITypeComparator {
    /**
     * Compare two values of unknown type
     *
     * @param contextValue The first value
     * @param value The second value
     * @param operator Compare operator to apply
     * @return <code>true</code> if the values are matching to the <code>operator</code>
     */
    boolean compare(Object contextValue, Object value, Operator operator);
}
