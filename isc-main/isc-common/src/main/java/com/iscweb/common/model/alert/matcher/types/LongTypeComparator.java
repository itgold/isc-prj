package com.iscweb.common.model.alert.matcher.types;

public class LongTypeComparator extends BaseTypeComparator<Long> {

    @Override
    protected Long convert(Object value) {
        return Long.valueOf(String.valueOf(value));
    }

    @Override
    protected int compare(Long value1, Long value2) {
        return value1.compareTo(value2);
    }
}
