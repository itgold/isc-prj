package com.iscweb.common.model.alert.matcher.types;

public class StringTypeComparator extends BaseTypeComparator<String> {

    @Override
    protected String convert(Object value) {
        return String.valueOf(value);
    }

    @Override
    protected int compare(String value1, String value2) {
        return value1.compareTo(value2);
    }
}
