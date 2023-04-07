package com.iscweb.simulator.exception;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String deviceId) {
        super("Device " + deviceId + " can not be found");
    }
}
