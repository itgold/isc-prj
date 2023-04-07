package com.iscweb.common.util

import spock.lang.Specification

class ObjectUtilsTest extends Specification {

    def "getBooleanWithDefault"() {
        given:
            Boolean result
            Boolean resultFalse = false
            Boolean resultTrue = true

        when:
            result = ObjectUtils.getBoolean(resultFalse)

        then:
            result == resultFalse

        when:
            result = ObjectUtils.getBoolean(resultTrue)

        then:
            result == resultTrue

        when:
            result = ObjectUtils.getBoolean(null)

        then:
            result == resultFalse
    }

    def "getBooleanWithoutDefault"() {
        given:
            Boolean result
            Boolean resultFalse = false
            Boolean resultTrue = true

        when:
            result = ObjectUtils.getBoolean(resultFalse, resultTrue)

        then:
            result == resultFalse

        when:
            result = ObjectUtils.getBoolean(null, resultTrue)

        then:
            result == resultTrue
    }
}
