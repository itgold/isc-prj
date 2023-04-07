package com.iscweb.common.util

import spock.lang.Specification

import java.util.regex.Pattern

class StringUtilsTest extends Specification {

    def "randomAlphanumericLength12"() {
        given:
            String result
            String result1
            String result2

        when:
            result = StringUtils.randomAlphanumeric()

        then:
            result
            result.length() == 12

        when:
            result1 = StringUtils.randomAlphanumeric()
            result2 = StringUtils.randomAlphanumeric()

        then:
            result1
            result2
            result1 != result2
    }

    def "randomAlphanumericLengthN"() {
        given:
            String result
            String result1
            String result2
            int num1 = 5
            int num2 = 15
            int numNegative = -1
            int numZero = 0

        when:
            result = StringUtils.randomAlphanumeric(num1)

        then:
            result
            result.length() == num1

        when:
            result1 = StringUtils.randomAlphanumeric(num1)
            result2 = StringUtils.randomAlphanumeric(num2)

        then:
            result1
            result2
            result1 != result2

        when:
            result = StringUtils.randomAlphanumeric(numNegative)

        then:
            result.length() == 0

        when:
            result = StringUtils.randomAlphanumeric(numZero)

        then:
            result.length() == numZero
    }

    def "decodeBody"() {
        given:
            String result
            String text = "text" + "\r\n" +
                    "new line text" + "\r" + "\r" +
                    "one more line text" + "\n" + "\n" + "\n" +
                    "last line" + "\r\n"

        when:
            result = StringUtils.decodeBody(text)

        then:
            result
            result == "text new line text  one more line text   last line "
    }

    def "setToString"() {
        given:
            String result
            Set<String> set = Set.of("camera", "drone", "speaker")
            Set<String> setEmpty = Set.of("")

        when:
            result = StringUtils.setToString(set)

        then:
            result
            //Java doc on lines 60 and 63 of StringUtils mentions @return a string of comma separated values
            //but method uses space, not comma as a separator
            for (String entry : set) {
                result.contains(entry)
            }
            result.length() == "camera".length() + 1 + "drone".length() + 1 + "speaker".length()

        when:
            result = StringUtils.setToString(setEmpty)

        then:
            result == ""
    }

    def "parseStringWithOnePattern"() {
        given:
            Set<String> result
            String inputText = "Once upon a time in a certain country there lived a king whose palace was surrounded by" +
                    " a spacious garden. But, though the gardeners were many and the soil was good, this garden yielded " +
                    "neither \n7 flowers nor fruits, not even grass or shady trees.\n\n" +
                    "The King was in despair about it, when a wise old man said to him:\n\n" +
                    "\"Your gardeners do not understand their business: but what can you expect of men whose fathers " +
                    "were cobblers and carpenters? How should they have learned to cultivate your garden?\" 1  22 "
            Pattern pattern = Pattern.compile("\\s\\w\\s")
            Pattern patternWithNoMatches = Pattern.compile("\\d\\d\\d")
            Set<String> expectedEmptySet = Set.of()

        when:
            result = StringUtils.parseString(inputText, pattern)

        then:
            result
            result == Set.of(" 1 ", " a ")

        when:
            result = StringUtils.parseString(inputText, patternWithNoMatches)

        then:
            result == expectedEmptySet
    }

    //TODO: better java doc description of this method; now it doesn't relay what this method actually does
    def "parseStringWithTwoPatterns"() {
        given:
            Set<String> result
            String inputText = "Once upon a time in a certain country there lived a king whose palace was surrounded by" +
                    " a spacious garden. But, though the1 gardeners were many and the soil was good, this garden yielded " +
                    "neither \n7 flowers nor fruits, not even grass or shady trees.\n\n" +
                    "The King was in despair about it, when a wise old man said to him:\n\n" +
                    "\"Your gardeners do not understand their business: 22 but what can you expect of men whose fathers " +
                    "were cobblers and carpenters? How should they have learned to cultivate your garden?\" 1  22 "
            Pattern patternParent = Pattern.compile("\\d\\d")
            Pattern patternSubParent = Pattern.compile("\\d")

        when:
            result = StringUtils.parseString(inputText, patternSubParent, patternParent)

        then:
            result == Set.of("1", "7")
    }

    def "isBlank"() {
        given:
            String usernameBlank = ""
            //String usernameSpaces = "       "
            String usernameNotBlank = "userName"
            Boolean result

        when:
            result = StringUtils.isBlank(usernameBlank)

        then:
            result

        when:
            result = StringUtils.isBlank(usernameNotBlank)

        then:
            !result

        when:
            result = StringUtils.isBlank(null)

        then:
            result

//        when:
//       //todo fail: check that string consisting of just spaces return true (username can't be spaces)
//        result = StringUtils.isBlank(usernameSpaces)

//        then:
//        result == true
    }

    def "capitalize"() {
        given:
            String result
            String text = "some text"
            String oneChar = "w"
            //String emptyString = ""

        when:
            result = StringUtils.capitalize(text)

        then:
            result
            result == "Some text"

        when:
            result = StringUtils.capitalize(oneChar)

        then:
            result
            result == "W"

        when:
            result = StringUtils.capitalize(null)

        then:
            result == null

//        when:
//        //todo: handle empty string otherwise StringIndexOutOfBoundsException is thrown
//        result = StringUtils.capitalize(emptyString)

//        then:
//        result
//        result == null
    }

    //this is just basic test, please expand with additional conditions
    def "bytesToHex"() {
        given:
            String result
            byte[] array = [69, 121, 33, 78, 98, 45, 118]

        when:
            result = StringUtils.bytesToHex(array)

        then:
            result
            result == "4579214E622D76"
    }

    def "hexToBin"() {
        given:
            int result
            char chNum = '0'
            char chUpper = 'E'
            char chLower = 'b'
            char outOfRange = 'G'

        when:
            result = StringUtils.hexToBin(chNum)

        then:
            result == 0

        when:
            result = StringUtils.hexToBin(chUpper)

        then:
            result
            result == 14

        when:
            result = StringUtils.hexToBin(chLower)

        then:
            result
            result == 11

        when:
            result = StringUtils.hexToBin(outOfRange)

        then:
            result
            result == -1
    }

    def "hexStringToBytes"() {
        given:
            byte[] resultArray
            String hexStringEvenLength = "6579214E622D76"
            String hexStringOddLength = "4579214E22D76"
            byte[] expectedResult = [101, 121, 33, 78, 98, 45, 118]

        when:
            resultArray = StringUtils.hexStringToBytes(hexStringOddLength)

        then:
            resultArray == null
            thrown(IllegalArgumentException)

        when:
            resultArray = StringUtils.hexStringToBytes(hexStringEvenLength)

        then:
            resultArray
            resultArray == expectedResult
    }
}
