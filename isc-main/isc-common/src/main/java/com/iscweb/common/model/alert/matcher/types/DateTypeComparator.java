package com.iscweb.common.model.alert.matcher.types;

import java.time.LocalDate;

public class DateTypeComparator extends BaseTypeComparator<LocalDate> {

    @Override
    protected LocalDate convert(Object value) {
        return LocalDate.parse(String.valueOf(value));
    }

    @Override
    protected int compare(LocalDate value1, LocalDate value2) {
        return value1.compareTo(value2);
    }
}
