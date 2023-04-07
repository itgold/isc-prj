package com.iscweb.common.util;

import com.fasterxml.jackson.databind.JsonNode;


public final class JsonUtils {

    private static final String DOT = "\\.";

    public static Integer safeIntField(String valuePath, JsonNode dataJSON) {
        JsonNode tmpNode = safePath(valuePath, dataJSON);
        return tmpNode !=null ? tmpNode.asInt() : null;
    }

    public static String safeStringField(String valuePath, JsonNode dataJSON) {
        JsonNode tmpNode = safePath(valuePath, dataJSON);
        return tmpNode !=null ? tmpNode.asText() : null;
    }

    private static JsonNode safePath(String valuePath, JsonNode dataJSON) {
        String [] sections =  valuePath.split(DOT);
        JsonNode tmpNode = null;

        for (String section: sections) {
            if (null == tmpNode && dataJSON.has(section)) {
                tmpNode = dataJSON.path(section);
            } else if (null != tmpNode && tmpNode.has(section)) {
                tmpNode = tmpNode.path(section);
            } else {
                tmpNode = null;
            }
        }
        return tmpNode;
    }
}
