package com.iscweb.search;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class CustomConverters {
    /**
     * {@link Converter} to read a {@link ZonedDateTime} from its {@link String} representation.
     */
    @ReadingConverter
    public enum StringToZonedDateTimeConverter implements Converter<String, ZonedDateTime> {

        INSTANCE;

        @Override
        public ZonedDateTime convert(String source) {
            return ZonedDateTime.parse(source, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
    }

    /**
     * {@link Converter} to write a {@link ZonedDateTime} to its {@link String} representation.
     */
    @WritingConverter
    public enum ZonedDateTimeToStringConverter implements Converter<ZonedDateTime, String> {

        INSTANCE;

        @Override
        public String convert(ZonedDateTime source) {
            return source.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }
    }

    /**
     * {@link Converter} to read a {@link ZonedDateTime} from its {@link String} representation.
     */
    @ReadingConverter
    public enum IntegerToDateConverter implements Converter<Integer, Date> {

        INSTANCE;

        @Override
        public Date convert(Integer source) {
            return source != null ? new Date(source.longValue()) : null;
        }
    }
}
