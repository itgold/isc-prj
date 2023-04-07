package com.iscweb.common.model.dto.api.v13;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Represents a range of ZonedDateTimes.
 * All timestamps of type Long are assumed to be in milliseconds since Unix epoch.
 */
public class ZonedDateTimeRange {

    ZonedDateTime from;
    ZonedDateTime to;

    public ZonedDateTimeRange(ZonedDateTime from, ZonedDateTime to) {
        setFrom(from);
        setTo(to);
    }

    public ZonedDateTimeRange(Long from, Long to) {
        setFrom(from);
        setTo(to);
    }

    /**
     * @return a ZonedDateTimeRange beginning at the Unix epoch and ending at current time
     */
    public static ZonedDateTimeRange getEpochTilNow() {
        ZonedDateTime from = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneId.systemDefault());
        ZonedDateTime to = ZonedDateTime.now();
        return new ZonedDateTimeRange(from, to);
    }

    public ZonedDateTime getFrom() {
        return from;
    }

    /**
     * Sets 'from', first checking that the given time precedes the value of 'to' if it exists.
     *
     * @param from the value
     */
    public void setFrom(ZonedDateTime from) {
        if (to != null && to.isBefore(from)) {
            throw new IllegalArgumentException("'to' value is before 'from' value");
        }
        this.from = from;
    }

    /**
     * Sets 'from', first checking that the given time precedes the value of 'to' if it exists.
     *
     * @param from the value in milliseconds since Unix epoch
     */
    public void setFrom(Long from) {
        setFrom(ZonedDateTime.ofInstant(Instant.ofEpochMilli(from), ZoneId.systemDefault()));
    }

    public ZonedDateTime getTo() {
        return to;
    }

    /**
     * Sets 'to', first checking that the given time follows the value of 'from' if it exists.
     *
     * @param to the value
     */
    public void setTo(ZonedDateTime to) {
        if (to != null && to.isBefore(from)) {
            throw new IllegalArgumentException("'to' value is before 'from' value");
        }
        this.to = to;
    }

    /**
     * Sets 'to', first checking that the given time follows the value of 'from' if it exists.
     *
     * @param to the value in milliseconds since Unix epoch
     */
    public void setTo(Long to) {
        setTo(ZonedDateTime.ofInstant(Instant.ofEpochMilli(to), ZoneId.systemDefault()));
    }

}
