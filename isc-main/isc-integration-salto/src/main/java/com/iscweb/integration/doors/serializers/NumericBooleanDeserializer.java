package com.iscweb.integration.doors.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

import java.io.IOException;

public class NumericBooleanDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {
        return !"0".equals(parser.getText());
    }

    public Object deserializeWithType(JsonParser parser, DeserializationContext context,
                                      TypeDeserializer typeDeserializer) throws IOException {
        // We could try calling:
        return typeDeserializer.deserializeTypedFromAny(parser, context);
    }
}
