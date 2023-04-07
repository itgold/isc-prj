package com.iscweb.integration.radios.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Enumeration for types of updates receiving from TRBOnet system.
 */
@JsonSerialize(using = TrboNetDeviceUpdateType.TrboNetDeviceUpdateTypeSerializer.class)
public enum TrboNetDeviceUpdateType {
    CREATED("Radio is added"),
    DELETED("Radio is disabled"),
    UPDATED("Radio is updated"),
    BULK_UPDATE("Multiple radios got updated"),
    RESTART("TRBOnet has restarted"),
    SYNC("Radio got synchronized");

    private final String description;

    TrboNetDeviceUpdateType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static TrboNetDeviceUpdateType forValues(String state) {
        for (TrboNetDeviceUpdateType deviceState : TrboNetDeviceUpdateType.values()) {
            if (deviceState.name().equalsIgnoreCase(state)) {
                return deviceState;
            }
        }

        return null;
    }

    public static class TrboNetDeviceUpdateTypeSerializer extends StdSerializer<TrboNetDeviceUpdateType> {
        public TrboNetDeviceUpdateTypeSerializer() {
            super(TrboNetDeviceUpdateType.class);
        }
        public TrboNetDeviceUpdateTypeSerializer(Class t) {
            super(t);
        }

        @Override
        public void serialize(TrboNetDeviceUpdateType state, JsonGenerator generator, SerializerProvider provider) throws IOException {
            generator.writeString(state.name());
        }
    }
}
