package com.iscweb.component.web.controller.graphql.common;

import com.google.common.collect.Maps;
import com.iscweb.common.exception.ServiceException;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;
import java.util.Map;

/**
 * Exception to be thrown when a GraphQL operation has failed.
 */
public class GraphQlExecutionException extends ServiceException implements GraphQLError {

    private final Throwable originalException;

    public GraphQlExecutionException(String msg, final Throwable originalException) {
        super(msg);
        this.originalException = originalException;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        Map<String, Object> customAttributes = Maps.newHashMap();
        customAttributes.put("errorMessage", this.originalException != null ? this.originalException.getMessage() : this.getMessage());
        return customAttributes;
    }

    public Throwable getOriginalException() {
        return originalException;
    }
}
