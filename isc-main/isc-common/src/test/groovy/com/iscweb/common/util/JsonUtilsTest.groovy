package com.iscweb.common.util

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

/**
 * Helper class that allows null safe value path
 */
class JsonUtilsTest extends Specification {

    private static ObjectMapper objectMapper = new ObjectMapper();

    def "test safeIntField"() {
        given:
            JsonNode json = objectMapper.valueToTree(input)

        expect:
            assert JsonUtils.safeIntField("user.id", json) == output

        where:
            input                      | output
            [user:[id:2345]]           | 2345
            [user:[id:"2345"]]         | 2345
            [:]                        | null
            [user:[:]]                 | null
            [account:[user:[id:9780]]] | null
    }

    def "test safeStringField"() {
        given:
            JsonNode json = objectMapper.valueToTree(input)

        expect:
            assert JsonUtils.safeStringField("user.id", json) == output

        where:
            input                        | output
            [user:[id:7890]]             | "7890"
            [user:[id:"7890"]]           | "7890"
            [:]                          | null
            [user:[:]]                   | null
            [account:[user:[id:"9780"]]] | null
    }
}
