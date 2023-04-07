package com.iscweb.integration.radios.events;

/**
 * List of all TRBOnet integration event types.
 */
public enum TrboNetEventTypes {
    EVENT_STREAM("trboNetStreamEvent");

    private final String code;

    TrboNetEventTypes(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}
