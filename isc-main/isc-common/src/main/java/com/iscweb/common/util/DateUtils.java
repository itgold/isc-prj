package com.iscweb.common.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class DateUtils {

    private static final int DATE_LENGTH = "YYYY-MM-DD".length();
    public static final ZonedDateTime DAY_IN_FUTURE = ZonedDateTime.now().plusYears(10);

    public static boolean hasTime(String dateTime) {
        return dateTime.indexOf("T") > 0;
    }

    public static boolean hasZone(String dateTime) {
        return dateTime.length() > DATE_LENGTH && (dateTime.lastIndexOf("-") >= DATE_LENGTH || dateTime.lastIndexOf("+") >= DATE_LENGTH);
    }

    public static ZonedDateTime parseAsZonedDateTime(String dateTime) {
        return parseAsZonedDateTime(dateTime, null);
    }

    public static ZonedDateTime parseAsZonedDateTime(String dateTime, ZonedDateTime defaultDateTime) {
        ZonedDateTime result = null;

        if (!StringUtils.isBlank(dateTime)) {
            try {
                if (hasZone(dateTime)) {
                    result = parseZonedDateTime(dateTime);
                } else if (!hasTime(dateTime)) {
                    result = parseDateAsZonedDateTime(dateTime);
                } else {
                    result = parseDateTimeAsZonedDateTime(dateTime);
                }
            } catch (DateTimeParseException e) {
                log.trace("Unable to parse date from string: {}", dateTime, e);
                result = defaultDateTime;
            }
        }

        return result;
    }

    private static ZonedDateTime parseDateAsZonedDateTime(String dateTime) {
        return LocalDate.parse(dateTime, DateTimeFormatter.ISO_DATE).atStartOfDay(ZoneId.systemDefault());
    }

    private static ZonedDateTime parseDateTimeAsZonedDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME).atZone(ZoneId.systemDefault());
    }

    private static ZonedDateTime parseZonedDateTime(String dateTime) {
        if (hasTime(dateTime)) {
            return ZonedDateTime.parse(dateTime);
        }

        // I didn't get parsing with date+zone work
        return ZonedDateTime.parse(dateTime);
    }
}
