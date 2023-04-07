package com.iscweb.common.util;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class handling string operations.
 */
@Slf4j
public class StringUtils {

    public static final String EMPTY = "";

    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    private static final char[] alphanumerics = ("0123456789" + "abcdefghijklmnopqrstuvwxyz"
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();


    /**
     * Generates a random alphanumeric string with a default length of 12.
     * @see this#randomAlphanumeric(int)
     */
    public static String randomAlphanumeric() {
        int length = 12;
        return randomAlphanumeric(length);
    }

    /**
     * Generates a random alphanumeric string.
     * @param   n string length.
     * @return  random string of size n
     */
    public static String randomAlphanumeric(int n) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            char randChar = alphanumerics[random.nextInt(alphanumerics.length)];
            builder.append(randChar);
        }

        return builder.toString();
    }

    public static String decodeBody(String text) {
        return text.replaceAll("\\r\\n|\\r|\\n", " ");
    }

    /**
     * Generates a string of comma-separated values from a set of strings.
     *
     * @param set a set of strings
     * @return a string of comma separated values
     */
    public static String setToString(Set<String> set) {
        return Joiner.on(" ").join(set);
    }

    // TODO(?): 3/10/17 We need a much better way to tokenize our texts before pattern matching.
    /**
     * Splits the input text into lines and looks for matches w.r.t a given pattern. The returned result is
     * a set of all matching strings.
     *
     * @param inputText input text to parse
     * @param pattern   regexp pattern
     * @return a set of matches
     */
    public static Set<String> parseString(String inputText, Pattern pattern) {
        Set<String> result = Sets.newTreeSet();
        try {
            String line;
            try (Scanner scan = new Scanner(new BufferedReader(new StringReader(inputText)))) {

                // We split into lines to avoid expensive pattern recognition on large texts.
                while (scan.hasNextLine()) {
                    line = scan.nextLine();
                    Matcher match = pattern.matcher(line);

                    while (match.find()) {
                        result.add(match.group());
                    }
                }
            }
        } catch (StackOverflowError e) {
            log.warn("Regex indicator pattern matching failed for {}. Avoid regex patterns containing repetitive " +
                             "alternative paths (For example, (a|b|c)*). These are compiled into a recursive call and result " +
                             "into a StackOverflow error when used on a very large string.", pattern, e);
        }
        return result;
    }

    /**
     * Splits the input text into lines and returns matches w.r.t. pattern1 if and only if pattern1
     * is not a sub-pattern of pattern2. The returned result is a set of all matching strings.
     * For example, if pattern1 is a SOFTWARE pattern, pattern2 is a URL pattern, and inputText is
     * "some text with an evil URL domain.com/evil.exe", then "evil.exe" will not be extracted as a
     * SOFTWARE since "domain.com/evil.exe" matches the URL pattern.
     *
     * @param inputText input text to parse
     * @param pattern1  regexp pattern1
     * @param pattern2  regexp pattern2
     * @return a set of matches
     */
    public static Set<String> parseString(String inputText, Pattern pattern1, Pattern pattern2) {
        Set<String> result = Sets.newTreeSet();
        try {
            String line;
            try (Scanner scan = new Scanner(new BufferedReader(new StringReader(inputText)))) {

                while (scan.hasNextLine()) {
                    line = scan.nextLine();
                    // We are assuming that patterns we care about break on white-spaces. We tokenize on white-spaces
                    // since we only want to avoid patterns matching sub-strings of the same word.
                    String[] lineSplitWhiteSpace = line.split(" ");

                    for (String word : lineSplitWhiteSpace) {
                        Matcher match1 = pattern1.matcher(word);
                        Matcher match2 = pattern2.matcher(word);
                        // A set containing all pattern2 matches.
                        Set<String> unwantedMatches = Sets.newTreeSet();
                        boolean matchNotFound;

                        // Add all pattern2 matches.
                        while (match2.find()) {
                            unwantedMatches.add(match2.group());
                        }

                        while (match1.find()) {
                            // We check if current match1 is a sub-string of any pattern2 extractions from current word.
                            matchNotFound = unwantedMatches.stream()
                                                           .noneMatch(unwantedMatch -> unwantedMatch.contains(match1.group()));
                            // If no match is found we add the match to the result.
                            if (matchNotFound) {
                                result.add(match1.group());
                            }
                        }
                    }
                }
            }
        } catch (StackOverflowError e) {
            log.warn("Regex indicator pattern matching failed for {} or {}. Avoid regex patterns containing repetitive " +
                             "alternative paths (For example, (a|b|c)*). These are compiled into a recursive call and result " +
                             "into a StackOverflow error when used on a very large string.", pattern1, pattern2, e);
        }
        return result;
    }

    /**
     * Inverted version of {@link StringUtils#isBlank(String)} method.
     * @param someString a string to test.
     * @return true if string is not empty or null.
     * @see StringUtils#isBlank(String)
     */
    public static boolean notBlank(String someString) {
        return !isBlank(someString);
    }

    /**
     * Validates if given string is null or empty.
     * @param someString a string for validation.
     * @return true if provided string is empty or null.
     */
    public static boolean isBlank(String someString) {
        return null == someString || someString.isEmpty();
    }

    public static String capitalize(String text) {
        String result = null;
        if (null != text) {
            result = text.substring(0, 1).toUpperCase()
                    + (text.length() > 1 ? text.substring(1) : StringUtils.EMPTY);
        }

        return result;
    }

    /**
     * Represent an array of bytes as a hex string.
     * Example output: OF1EDA
     *
     * @param bytes Byte array
     * @return hex representation of input bytes
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static int hexToBin(char ch) {
        int result = -1;
        if ('0' <= ch && ch <= '9') {
            result = ch - '0';
        } else if ('A' <= ch && ch <= 'F') {
            result = ch - 'A' + 10;
        } else if ('a' <= ch && ch <= 'f') {
            result = ch - 'a' + 10;
        }
        return result;
    }

    public static byte[] hexStringToBytes(String hexString) {
        final int len = hexString.length();
        if (len % 2 != 0) {
            throw new IllegalArgumentException("hexStringToBytes needs to be even-length: " + hexString);
        }

        byte[] out = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            int h = hexToBin(hexString.charAt(i));
            int l = hexToBin(hexString.charAt(i + 1));
            if (h == -1 || l == -1) {
                throw new IllegalArgumentException("contains illegal character for hexStringToBytes: " + hexString);
            }

            out[i / 2] = (byte) (h * 16 + l);
        }

        return out;
    }
}
