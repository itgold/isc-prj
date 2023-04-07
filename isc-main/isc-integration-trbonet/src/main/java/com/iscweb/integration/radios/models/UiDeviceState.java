package com.iscweb.integration.radios.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.iscweb.common.model.metadata.RadioConnectionStatus;

import java.io.IOException;

/**
 * Simple online state for radio device received from the TRBOnet system.
 */
@JsonSerialize(using = UiDeviceState.UIDeviceStateSerializer.class)
public enum UiDeviceState {
    ONLINE_GPS(0x1, RadioConnectionStatus.ONLINE_GPS),
    ONLINE_NO_GPS(0x2, RadioConnectionStatus.ONLINE_NO_GPS),
    OFFLINE(0x4, RadioConnectionStatus.OFFLINE),
    ONLINE_BEACON_DETECTED(0x8, RadioConnectionStatus.ONLINE_BEACON_DETECTED);

    private final int code;
    private final RadioConnectionStatus connectionStatus;

    UiDeviceState(int code, RadioConnectionStatus connectionStatus) {
        this.code = code;
        this.connectionStatus = connectionStatus;
    }

    public int getCode() {
        return code;
    }

    public RadioConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    @JsonCreator
    public static UiDeviceState forValues(String stateStr) {
        int state = Integer.parseInt(stateStr);
        for (UiDeviceState deviceState : UiDeviceState.values()) {
            if (deviceState.getCode() == state) {
                return deviceState;
            }
        }

        return null;
    }

    public static class UIDeviceStateSerializer extends StdSerializer<UiDeviceState> {
        public UIDeviceStateSerializer() {
            super(UiDeviceState.class);
        }
        public UIDeviceStateSerializer(Class t) {
            super(t);
        }

        @Override
        public void serialize(UiDeviceState state, JsonGenerator generator, SerializerProvider provider) throws IOException {
            generator.writeString(String.valueOf(state.code));
        }
    }
}
