package com.iscweb.component.web.controller.graphql.common;

import com.iscweb.common.model.dto.IDto;
import graphql.Assert;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;

/**
 * DTO class for storing invocation data.
 */
public class GraphQlInvocationDataDto implements IDto {

    @Getter
    private final String query;

    @Getter
    private final String operationName;

    @Getter
    private final Map<String, Object> variables;

    public GraphQlInvocationDataDto(String query, String operationName, Map<String, Object> variables) {
        this.query = Assert.assertNotNull(query, () -> "query must be provided");
        this.operationName = operationName;
        this.variables = variables != null ? variables : Collections.emptyMap();
    }
}
