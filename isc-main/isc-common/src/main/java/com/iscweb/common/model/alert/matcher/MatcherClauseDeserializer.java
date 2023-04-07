package com.iscweb.common.model.alert.matcher;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.iscweb.common.util.ObjectMapperUtility;

import java.io.IOException;
import java.util.List;

/**
 * Helper utility provides deserialization for the alert trigger matcher item from JSON.
 */
public class MatcherClauseDeserializer extends JsonDeserializer<MatcherClause> {

    @Override
    public MatcherClause deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        MatcherClause result = new MatcherClause();
        JsonNode node = jp.getCodec().readTree(jp);

        JsonNode andNode = node.get("and");
        if (andNode != null) {
            result.setAnd(ObjectMapperUtility.fromJson(andNode.toString(), MatcherClause.class));
        }

        JsonNode orNode = node.get("or");
        if (orNode != null) {
            result.setOr(ObjectMapperUtility.fromJson(orNode.toString(), MatcherClause.class));
        }

        JsonNode valuesNode = node.get("items");
        if (valuesNode != null && valuesNode.isArray()) {
            List<ItemClause> items = ObjectMapperUtility.fromJson(valuesNode.toString(), new TypeReference<>() {});
            for (ItemClause item : items) {
                result.add(item);
            }
        }

        return result;
    }
}