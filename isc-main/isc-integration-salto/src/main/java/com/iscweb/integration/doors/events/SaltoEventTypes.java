package com.iscweb.integration.doors.events;

public enum SaltoEventTypes {
    EVENT_STREAM("saltoStreamEvent");

    private final String code;

    SaltoEventTypes(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}
