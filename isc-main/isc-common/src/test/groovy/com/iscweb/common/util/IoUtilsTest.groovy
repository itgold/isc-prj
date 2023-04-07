package com.iscweb.common.util

import spock.lang.Specification

class IoUtilsTest extends Specification {
    //todo: change method name to start with lowercase in IoUtils
    def "getReversedSubarray"() {
        given:
            byte[] array = [69, 121, 33, 78, 98, 45, 118]
            byte[] resultArray
            int start = 3
            int length = 4
            int startZero = 0
            int lengthZero = 0
            //int startFail = 6
            byte[] expectedWithStart = [118, 45, 98, 78]
            byte[] expectedFromZero = [78, 33, 121, 69]
            byte[] expectedEmpty = []

        when:
            resultArray = IoUtils.GetReversedSubarray(array, start, length)

        then:
            resultArray
            resultArray == expectedWithStart

        when:
            resultArray = IoUtils.GetReversedSubarray(array, startZero, length)

        then:
            resultArray
            resultArray == expectedFromZero

        when:
            resultArray = IoUtils.GetReversedSubarray(array, start, lengthZero)

        then:
            resultArray == expectedEmpty

//        when:
//       //todo important fail: should check that (start + length) are not exceeding array size
//        resultArray = IoUtils.GetReversedSubarray(array, startFail, length)
//
//        then:
//        ArrayIndexOutOfBoundsException outOfBound = thrown()

//        when:
//       //todo fail: check that array is not null
//        resultArray = IoUtils.GetReversedSubarray(null, start, length)
//
//        then:
//        final NullPointerException exception = thrown()

//        when:
//       //todo: not sure if should check that start parameter is not negative
//        resultArray = IoUtils.GetReversedSubarray(array, -3, length)
//
//        then:
//        ArrayIndexOutOfBoundsException outOfBound = thrown()

//        when:
//       //todo: check that length parameter is not negative
//        resultArray = IoUtils.GetReversedSubarray(array, start, -4)
//
//        then:
//        NegativeArraySizeException negativeArraySize = thrown()
    }
}
