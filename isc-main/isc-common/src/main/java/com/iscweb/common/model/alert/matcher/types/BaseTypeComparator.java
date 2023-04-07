package com.iscweb.common.model.alert.matcher.types;

import com.iscweb.common.model.alert.matcher.Operator;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Base class for helper type conversion utilities used by alert trigger matchers.
 * To help to extract/compare values with specific type from the matching context.
 *
 * @param <T> Type the converter working with.
 */
public abstract class BaseTypeComparator<T> implements ITypeComparator {

    protected abstract T convert(Object value);

    protected abstract int compare(T value1, T value2);

    @Override
    public boolean compare(Object contextValue, Object value, Operator operator) {
        boolean result = false;

        if (Operator.IN == operator) {
            result = Arrays.stream(String.valueOf(value).split(","))
                    .map(this::convert)
                    .collect(Collectors.toList())
                    .contains((T) contextValue);
        } else {
            int rez = compare((T) contextValue, convert(value));
            switch (operator) {
                case E: result = rez == 0; break;
                case L: result = rez < 0; break;
                case LE: result = rez < 0 || rez == 0; break;
                case G: result = rez > 0; break;
                case GE: result = rez > 0 || rez == 0; break;
            }
        }

        return result;
    }
}
