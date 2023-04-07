package com.iscweb.common.sis.utils;

import com.google.common.collect.Lists;
import com.iscweb.common.sis.annotations.SisServiceMethod;
import com.iscweb.common.sis.annotations.SisServiceMethodParam;
import com.iscweb.common.sis.model.SisFieldMetadata;
import com.iscweb.common.sis.model.SisMethodMetadata;
import com.iscweb.common.sis.model.SisServiceMetadata;
import com.iscweb.common.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Helper utility class to inspect and generate metadata about third party
 * API call using Java reflection.
 */
public final class MetadataIntrospector {

    /**
     * Primary method for class metadata inspection.
     * @param serviceClass service class to inspect.
     * @return simple integration service metadata object instance.
     */
    public static SisServiceMetadata inspect(Class<?> serviceClass) {

        SisServiceMetadata result = new SisServiceMetadata(serviceClass.getSimpleName(), Lists.newArrayList());
        Method[] methods = serviceClass.getDeclaredMethods();
        for (Method method : methods) {
            SisMethodMetadata methodMeta = new SisMethodMetadata();
            methodMeta.setKey(generateMethodKey(method));
            methodMeta.setArgs(Lists.newArrayList());
            methodMeta.setResponseType(method.getReturnType());
            SisServiceMethod methodAnnotation = method.getAnnotation(SisServiceMethod.class);
            if (null != methodAnnotation) {
                methodMeta.setName(methodAnnotation.value());
            } else {
                methodMeta.setName(method.getName());
            }

            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                methodMeta.getArgs().add(getFieldMetadata(parameter));
            }

            result.getMethods().add(methodMeta);
        }

        return result;
    }

    /**
     * Retrieves given parameter field metadata.
     * @param parameter a parameter for metadata extraction.
     * @return parameter's metadata.
     */
    protected static SisFieldMetadata getFieldMetadata(Parameter parameter) {

        SisFieldMetadata result = new SisFieldMetadata();
        SisServiceMethodParam paramAnnotation = parameter.getAnnotation(SisServiceMethodParam.class);
        if (null != paramAnnotation) {
            result.setName(paramAnnotation.value());
        } else {
            result.setName(StringUtils.capitalize(parameter.getName()));
        }
        result.setType(resolveFieldType(parameter));

        return result;
    }

    /**
     * Detects the field type of a given parameter.
     * @param parameter parameter for type detection.
     * @return identified field type.
     */
    protected static SisFieldMetadata.Types resolveFieldType(Parameter parameter) {

        SisFieldMetadata.Types result = SisFieldMetadata.Types.OBJECT;

        final Class<?> type = parameter.getType();

        if (String.class.isAssignableFrom(type)) {
            result = SisFieldMetadata.Types.STRING;
        } else if (Boolean.class.isAssignableFrom(type)) {
            result = SisFieldMetadata.Types.BOOL;
        } else if (Integer.class.isAssignableFrom(type)) {
            result = SisFieldMetadata.Types.INTEGER;
        } else if (Long.class.isAssignableFrom(type)) {
            result = SisFieldMetadata.Types.LONG;
        } else if (Double.class.isAssignableFrom(type)) {
            result = SisFieldMetadata.Types.DOUBLE;
        }

        return result;
    }

    /**
     * Generate unique method key.
     * @param method a method for a key generation.
     * @return a new key for the given method.
     */
    public static String generateMethodKey(Method method) {
        StringBuilder result = new StringBuilder(method.getName());
        final Class<?>[] params = method.getParameterTypes();
        if (params.length > 0) {
            for (Class<?> type : params) {
                result.append(":").append(type.getCanonicalName());
            }
        }
        return result.toString();
    }
}
