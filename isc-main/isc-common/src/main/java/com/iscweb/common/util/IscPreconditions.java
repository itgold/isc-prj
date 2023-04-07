package com.iscweb.common.util;

import com.iscweb.common.exception.InvalidOperationException;

import java.util.Objects;

/**
 * Utility class with a set of methods for parameter validation.
 * Inspired by Guava's {@link com.google.common.base.Preconditions} class.
 * The difference between this and Guava's class is that this class uses the Pha prefix and throws
 * our internal checked exceptions.
 */
public class IscPreconditions {

    /**
     * Validates the given expression and throws ServiceException if it is not true.
     *
     * @param expression   expression to validate.
     * @param errorMessage error message to be thrown if expression is false.
     * @throws InvalidOperationException if expression evaluated to false.
     */
    public static void must(boolean expression, String errorMessage) throws InvalidOperationException {
        if (!expression) {
            throw new InvalidOperationException(errorMessage);
        }
    }

    /**
     * Validates the given expression and throws Exception if it is not false.
     *
     * @param expression   expression to validate.
     * @param errorMessage error message to be thrown if expression is true.
     * @throws InvalidOperationException if expression evaluated to true.
     */
    public static void mustNot(boolean expression, String errorMessage) throws InvalidOperationException {
        if (expression) {
            throw new InvalidOperationException(errorMessage);
        }
    }

    /**
     * Checks that the given object is not null. Exception is thrown otherwise.
     *
     * @param object       object for null checking.
     * @param errorMessage error message for exception.
     * @throws InvalidOperationException if object is null.
     */
    public static void mustNotNull(Object object, String errorMessage) throws InvalidOperationException {
        if (Objects.isNull(object)) {
            throw new InvalidOperationException(errorMessage);
        }
    }
}
