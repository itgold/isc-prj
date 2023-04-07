package com.iscweb.common.model.alert.matcher;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Alert trigger matcher constraint comparison operation
 */
public enum Operator {
    E("=="),
    L("<"),
    LE("<="),
    G(">"),
    GE(">="),
    /**
     * value is one of the list
     */
    IN("in");

    private final String code;

    Operator(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @JsonCreator
    public static Operator forValue(String code) {
        Operator result = Operator.E;
        for (Operator value : values()) {
            if (value.getCode().equals(code)) {
                result = value;
            }
        }

        return result;
    }

    @JsonValue
    public String toValue() {
        return this.getCode();
    }
}
