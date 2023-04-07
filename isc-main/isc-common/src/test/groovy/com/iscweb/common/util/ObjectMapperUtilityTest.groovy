package com.iscweb.common.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.google.common.collect.Lists
import spock.lang.Specification

class ObjectMapperUtilityTest extends Specification {

    def "initAndGetObjectMapper"() {
        given:
            ObjectMapper objectMapper = new ObjectMapper()

        when:
            ObjectMapperUtility.init(objectMapper)
            ObjectMapper result = ObjectMapperUtility.getObjectMapper()

        then:
            result
            result == objectMapper
    }

    def "toJson"() {
        given:
            String result
            String expectedJson = "[\"SF\",\"Seattle\",\"Oregon\"]"
            List<String> list = Lists.newArrayList("SF", "Seattle", "Oregon")

        when:
            result = ObjectMapperUtility.toJson(list)

        then:
            result
            expectedJson == result

        when:
            result = ObjectMapperUtility.toJson(null)

        then:
            !result
    }

    def "fromJson"() {
        given:
            List<String> result
            String json = "[\"SF\",\"Seattle\",\"Oregon\"]"
            List<String> expectedResult = Lists.newArrayList("SF", "Seattle", "Oregon")

        when:
            result = ObjectMapperUtility.fromJson(json, ArrayList.class)

        then:
            result
            result == expectedResult

        when:
            result = ObjectMapperUtility.fromJson(null, ArrayList.class)

        then:
            !result

        when:
            ObjectMapperUtility.fromJson(json, HashMap.class)

        then:
            thrown(MismatchedInputException)
    }
}