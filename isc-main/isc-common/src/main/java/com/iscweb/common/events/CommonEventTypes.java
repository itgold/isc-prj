package com.iscweb.common.events;

public enum CommonEventTypes {
    // Common
    UPDATE("update"),
    USER_SYNC("userSync"),

    // Common Camera events
    CAMERA_SYNC("cameraSync"),
    CAMERA_CONNECTED("connected"),
    CAMERA_DISCONNECTED("disconnected"),
    CAMERA_STATUS("cameraStatus"),
    CAMERA_UPDATE("cameraUpdate"),

    // Common Door events
    DOOR_SYNC("doorSync"),
    DOOR_UPDATE("doorUpdate"),

    // Common Radio events
    RADIO_SYNC("radioSync"),
    ;

    private final String code;

    CommonEventTypes(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}
