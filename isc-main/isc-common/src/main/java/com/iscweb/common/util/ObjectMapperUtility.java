package com.iscweb.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Utility class for operations involving conversions between objects and JSON representations.
 */
public class ObjectMapperUtility {

    private static ObjectMapper objectMapper;

    /**
     * Called by ApplicationInitializerComponent to pass configured ObjectMapper.
     *
     * @param objectMapper the application's configured object mapper
     */
    public static void init(ObjectMapper objectMapper) {
        ObjectMapperUtility.objectMapper = objectMapper;
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * Transforms the given object into its JSON representation.
     * This uses the default ObjectMapper configured by Spring.
     *
     * @param object the object to serialize
     * @return a string representation (or null)
     */
    public static String toJson(Object object) throws JsonProcessingException {
        return object != null ? getObjectMapper().writeValueAsString(object) : null;
    }

    /**
     * Transforms the given JSON string into an object of the given type.
     * This uses the default ObjectMapper configured by Spring.
     *
     * @param jsonString the JSON string to transform
     * @param clazz      the class of the object that the string should be deserialized into
     * @return a deserialized object
     */
    public static <T> T fromJson(String jsonString, Class<T> clazz) throws IOException {
        return jsonString != null ? getObjectMapper().readValue(jsonString, clazz) : null;
    }

    /**
     * Transforms the given JSON string into an object of the given type.
     * This uses the default ObjectMapper configured by Spring.
     *
     * @param jsonString    the JSON string to transform
     * @param typeReference a type reference object used to dynamically convert the string
     * @return a deserialized object
     */
    public static <T> T fromJson(String jsonString, TypeReference<T> typeReference) throws IOException {
        return jsonString != null ? getObjectMapper().readValue(jsonString, typeReference) : null;
    }
}
