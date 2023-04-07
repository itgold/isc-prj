package com.iscweb.component.web.controller.graphql.common;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import graphql.execution.ResultPath;
import graphql.language.SourceLocation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class GraphQlExecutionStrategy extends AsyncExecutionStrategy {

    public GraphQlExecutionStrategy() {
        super(new CustomDataFetcherExceptionHandler());
    }

    private static class CustomDataFetcherExceptionHandler implements DataFetcherExceptionHandler {

        @Override
        public DataFetcherExceptionHandlerResult onException(DataFetcherExceptionHandlerParameters handlerParameters) {
            SourceLocation sourceLocation = handlerParameters.getSourceLocation();
            ResultPath path = handlerParameters.getPath();

            Throwable originalError = handlerParameters.getException();
            GraphQlExecutionException exception = generateError(path, originalError, 5);
            if (exception == null) {
                exception = new GraphQlExecutionException(formatMessage(path, originalError), originalError);
            }

            log.warn(exception.getMessage(), originalError);
            return DataFetcherExceptionHandlerResult
                    .newResult()
                    .error(new GraphQlErrorAdaptor(exception))
                    .build();
        }

        private GraphQlExecutionException generateError(ResultPath path, Throwable exception, int maxDepth) {
            GraphQlExecutionException error = null;
            if (exception != null && maxDepth > 0) {
                if (exception instanceof GraphQlExecutionException) {
                    Throwable originalException = ((GraphQlExecutionException) exception).getOriginalException();
                    error = new GraphQlExecutionException(formatMessage(path, exception), originalException);
                } else {
                    error = generateError(path, exception.getCause(), maxDepth - 1);
                }
            }

            return error;
        }

        private String formatMessage(ResultPath path, Throwable exception) {
            return String.format("Exception while fetching data (%s) : %s", path, exception.getMessage());
        }
    }

    private static class GraphQlErrorAdaptor implements GraphQLError {

        private final GraphQLError graphQLError;

        public GraphQlErrorAdaptor(GraphQLError graphQlError) {
            this.graphQLError = graphQlError;
        }

        @Override
        public List<SourceLocation> getLocations() {
            return graphQLError.getLocations();
        }

        @Override
        public ErrorClassification getErrorType() {
            return graphQLError.getErrorType();
        }

        @Override
        public String getMessage() {
            return graphQLError.getMessage();
        }

        @Override
        public Map<String, Object> getExtensions() {
            return graphQLError.getExtensions();
        }
    }
}
