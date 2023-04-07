package com.iscweb.component.web.controller.graphql.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iscweb.component.web.controller.IApplicationController;
import com.iscweb.component.web.util.GraphQlUtils;
import com.iscweb.service.security.IscPrincipal;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLContext;
import graphql.kickstart.tools.SchemaParserOptions;
import graphql.schema.GraphQLSchema;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoaderRegistry;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static com.iscweb.common.util.StringUtils.decodeBody;

/**
 * Base class for all GraphQL controllers.
 * All GraphQL endpoint controllers must provide a specific <code>GraphQL</code> instance.
 */
@Slf4j
public abstract class BaseGraphQlController implements IApplicationController {

    @Setter(onMethod = @__({@Autowired(required = false)}))
    private DataLoaderRegistry dataLoaderRegistry;

    @Setter(onMethod = @__({@Autowired}))
    private ObjectMapper objectMapper;

    protected abstract GraphQL getGraphQl(IscPrincipal principal);

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> graphQlPostHandler(@RequestHeader(value = HttpHeaders.CONTENT_TYPE, required = false) String contentType,
                                                     @RequestParam(value = "query", required = false) String query,
                                                     @RequestParam(value = "operationName", required = false) String operationName,
                                                     @RequestParam(value = "variables", required = false) String variablesJson,
                                                     @RequestBody(required = false) String body,
                                                     @AuthenticationPrincipal IscPrincipal principal) {
        ResponseEntity<Object> result = null;
        if (body == null) {
            body = "";
        }

        // https://graphql.org/learn/serving-over-http/#post-request
        //
        // A standard GraphQL POST request should use the application/json content type,
        // and include a JSON-encoded body of the following form:
        //
        // {
        //   "query": "...",
        //   "operationName": "...",
        //   "variables": { "myVariable": "someValue", ... }
        // }

        if (MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
            GraphQlRequestBodyDto request = deserialize(decodeBody(body), GraphQlRequestBodyDto.class);
            if (request.getQuery() == null) {
                request.setQuery("");
            }
            result = new ResponseEntity<>(executeRequest(request.getQuery(),
                                                         request.getOperationName(),
                                                         request.getVariables(),
                                                         principal), HttpStatus.OK);
        } else if (query != null) {
            // In addition to the above, we recommend supporting two additional cases:
            //
            // * If the "query" query string parameter is present (as in the GET example above),
            //   it should be parsed and handled in the same way as the HTTP GET case.
            result = new ResponseEntity<>(executeRequest(query,
                                                         operationName,
                                                         convertVariablesJson(variablesJson),
                                                         principal), HttpStatus.OK);
        } else if ("application/graphql".equals(contentType)) {
            // * If the "application/graphql" Content-Type header is present,
            //   treat the HTTP POST body contents as the GraphQL query string.
            result = new ResponseEntity<>(executeRequest(decodeBody(body),
                                                         null,
                                                         null,
                                                         principal), HttpStatus.OK);
        }

        if (result == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Could not process GraphQL request");
        }

        return result;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> graphQlGetHandler(@RequestParam("query") String query,
                                                    @RequestParam(value = "operationName", required = false) String operationName,
                                                    @RequestParam(value = "variables", required = false) String variablesJson,
                                                    @AuthenticationPrincipal IscPrincipal principal) {
        // https://graphql.org/learn/serving-over-http/#get-request
        //
        // When receiving an HTTP GET request, the GraphQL query should be specified in the "query" query string.
        // For example, if we wanted to execute the following GraphQL query:
        //
        // {
        //   me {
        //     name
        //   }
        // }
        //
        // This request could be sent via an HTTP GET like so:
        //
        // http://myapi/graphql?query={me{name}}
        //
        // Query variables can be sent as a JSON-encoded string in an additional query parameter called "variables".
        // If the query contains several named operations,
        // an "operationName" query parameter can be used to control which one should be executed.

        return new ResponseEntity<>(executeRequest(query,
                                                   operationName,
                                                   convertVariablesJson(variablesJson),
                                                   principal), HttpStatus.OK);
    }

    /**
     * Converts the given JSON string into a map of key-value pairs.
     * @param jsonMap input json string.
     * @return a map of key values parsed from given json.
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> convertVariablesJson(String jsonMap) {
        Map<String, Object> result = Collections.emptyMap();
        if (jsonMap != null) {
            result = deserialize(jsonMap, Map.class);
        }
        return result;
    }

    private ExecutionResult executeRequest(
            String query,
            String operationName,
            Map<String, Object> variables,
            IscPrincipal principal) {
        GraphQlInvocationDataDto invocationData = new GraphQlInvocationDataDto(query, operationName, variables);

        GraphQLContext.Builder builder = GraphQLContext.newContext();
        builder.of(GraphQlUtils.PRINCIPAL, principal);

        ExecutionInput.Builder executionInputBuilder = ExecutionInput.newExecutionInput()
                .context(builder.build())
                .query(invocationData.getQuery())
                .operationName(invocationData.getOperationName())
                .variables(invocationData.getVariables());
        if (dataLoaderRegistry != null) {
            executionInputBuilder.dataLoaderRegistry(dataLoaderRegistry);
        }
        ExecutionInput executionInput = executionInputBuilder.build();

        return getGraphQl(principal).execute(executionInput);
    }

    /**
     * Customized GraphQL schema parser options to override default <code>ObjectMapper</code> implementation and
     * allow the use of automatic change tracking for all GraphQL queries and mutations input DTO parameters.
     *
     * @return GraphQl schema parser options
     */
    protected SchemaParserOptions parserOptions() {
        SchemaParserOptions options = SchemaParserOptions.defaultOptions();
        GraphQlObjectMapperDecorator provider = new GraphQlObjectMapperDecorator(options.getObjectMapperProvider());
        return options.copy(options.getContextClass(), options.getGenericWrappers(), options.getAllowUnimplementedResolvers(), provider,
                options.getProxyHandlers(), options.getInputArgumentOptionalDetectOmission(), options.getPreferGraphQLResolver(),
                options.getIntrospectionEnabled(), options.getCoroutineContextProvider(), options.getTypeDefinitionFactories(),
                options.getFieldVisibility(), options.getIncludeUnusedTypes());
    }

    /**
     * Build new GraphQL engine based on provided schema.
     *
     * @param schema GraphQL schema used to create GraphQL object
     * @return GraphQL object
     */
    protected GraphQL buildEngine(GraphQLSchema schema) {
        return GraphQL.newGraphQL(schema)
                // customize execution strategy with custom error handling
                .queryExecutionStrategy(new GraphQlExecutionStrategy())
                // Default strategy: AsyncSerialExecutionStrategy
                .mutationExecutionStrategy(new GraphQlExecutionStrategy())
                .build();
    }

    protected <T> T deserialize(String json, Class<T> requiredType) {
        try {
            return objectMapper.readValue(json, requiredType);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing object from JSON: " + e.getMessage(), e);
        }
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
