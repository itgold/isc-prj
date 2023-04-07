package com.iscweb.persistence.repositories

import com.iscweb.common.util.DateUtils
import spock.lang.Specification

import java.time.Month
import java.time.ZonedDateTime


class TestDateTime extends Specification {

    def "Test parsing"() {
        given: "define some date strings"
            def dateStr = '2021-06-04'
            def dateStr2 = '2021-06-04T10:15:30'
            def dateStr3 = '2021-06-04T16:24:11.252+05:30[Asia/Calcutta]'
            def dateStr31 = '2021-06-04T16:24:11.252+05:30'

        when: "parse dates"
            ZonedDateTime result1 = DateUtils.parseAsZonedDateTime(dateStr)
            ZonedDateTime result2 = DateUtils.parseAsZonedDateTime(dateStr2)
            ZonedDateTime result3 = DateUtils.parseAsZonedDateTime(dateStr3)
            ZonedDateTime result31 = DateUtils.parseAsZonedDateTime(dateStr31)

        then: "check date parsing"
            result1 != null
            result1.getYear() == 2021
            result1.getMonth() == Month.JUNE
            result1.getDayOfMonth() == 4

        and: "check date with time parsing"
            result2 != null
            result2.getYear() == 2021
            result2.getMonth() == Month.JUNE
            result2.getDayOfMonth() == 4

        and: "check date with time and timezone parsing"
            result3 != null
            result31 != null
            result3.getYear() == 2021
            result3.getMonth() == Month.JUNE
            result3.getDayOfMonth() == 4
    }
}