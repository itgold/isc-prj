package com.iscweb.component.web.controller.graphql.common;

import com.iscweb.common.model.dto.IDto;
import lombok.Data;
import org.springframework.data.domain.Sort;

/**
 * DTO class used as a standard schema for requests that require sorting and ordering parameters.
 */
@Data
public class SortOrderDto implements IDto {

    private String property;
    private Sort.Direction direction;

}
