package com.iscweb.common.util

import spock.lang.Specification

class ValidationUtilsTest extends Specification {

    def "isNotEmpty"() {
        given:
            String textEmpty = ""
            //String whiteSpace = " "
            String errorMessage = "error"

        when:
            ValidationUtils.isNotEmpty(textEmpty, errorMessage)

        then:
            IllegalArgumentException exception = thrown()
            exception.getMessage() == errorMessage

//        when:
//        //todo : StringUtils.isBlank(text) doesn't check for white spaces as illegal username
//        ValidationUtils.isNotEmpty(whiteSpace, errorMessage);
//
//        then:
//        exception = thrown()
//        exception.getMessage() == errorMessage

    }

    def "isNotNull"() {
        given:
            String textNull = null
            String errorMessage = "error"
            String textNotNull = "notNull"

        when:
            ValidationUtils.isNotNull(textNull, errorMessage)

        then:
            IllegalArgumentException exception = thrown()
            exception.getMessage() == errorMessage

        when:
            ValidationUtils.isNotNull(textNotNull, errorMessage)

        then:
            notThrown(IllegalArgumentException)
    }
}
