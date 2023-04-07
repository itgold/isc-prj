package com.iscweb.common.model.alert.matcher;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Helper utility provides serialization for the alert trigger matcher item into JSON.
 */
public class MatcherClauseSerializer extends JsonSerializer<MatcherClause> {
    @Override
    public void serialize(MatcherClause toSerialize, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        if (toSerialize.getAnd() != null) {
            jgen.writeObjectField("and", toSerialize.getAnd());
        }
        if (toSerialize.getOr() != null) {
            jgen.writeObjectField("or", toSerialize.getOr());
        }

        provider.defaultSerializeField("items", toSerialize.iterator(), jgen);
        jgen.writeEndObject();
    }
}