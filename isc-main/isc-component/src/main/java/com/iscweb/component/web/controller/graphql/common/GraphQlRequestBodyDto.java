package com.iscweb.component.web.controller.graphql.common;

import com.iscweb.common.model.dto.IDto;
import lombok.Data;

import java.util.Map;

/**
 * DTO class for storing request body data.
 */
@Data
public class GraphQlRequestBodyDto implements IDto {

    private String query;
    private String operationName;
    private Map<String, Object> variables;

}
