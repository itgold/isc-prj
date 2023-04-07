package com.iscweb.common.model.alert.matcher.types;

import java.time.LocalTime;

public class TimeTypeComparator extends BaseTypeComparator<LocalTime> {

    @Override
    protected LocalTime convert(Object value) {
        return LocalTime.parse(String.valueOf(value));
    }

    @Override
    protected int compare(LocalTime value1, LocalTime value2) {
        return value1.compareTo(value2);
    }
}
