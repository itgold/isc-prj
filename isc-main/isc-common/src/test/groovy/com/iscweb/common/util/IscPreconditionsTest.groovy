package com.iscweb.common.util

import com.iscweb.common.exception.InvalidOperationException
import spock.lang.Specification

class IscPreconditionsTest extends Specification {

    def "must"() {
        given:
            Boolean expressionTrue = true
            Boolean expressionFalse = false
            String errorMessage = "error"

        when:
            IscPreconditions.must(expressionFalse, errorMessage)

        then:
            thrown(InvalidOperationException)

        when:
            IscPreconditions.must(expressionTrue, errorMessage)

        then:
            notThrown(InvalidOperationException)
    }

    def "mustNot"() {
        given:
            Boolean expressionTrue = true
            Boolean expressionFalse = false
            String errorMessage = "error"

        when:
            IscPreconditions.mustNot(expressionTrue, errorMessage)

        then:
            thrown(InvalidOperationException)

        when:
            IscPreconditions.mustNot(expressionFalse, errorMessage)

        then:
            notThrown(InvalidOperationException)
    }

    def "mustNotNull"() {
        given:
            Object objectNull = null
            Object objectNotNull = new Object()
            String errorMessage = "error"

        when:
            IscPreconditions.mustNotNull(objectNull, errorMessage)

        then:
            thrown(InvalidOperationException)

        when:
            IscPreconditions.mustNotNull(objectNotNull, errorMessage)

        then:
            notThrown(InvalidOperationException)
    }
}
