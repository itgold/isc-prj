package com.iscweb.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * A DTO which is used to represent the fields requested by a client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectionDto implements IDto {

    private String columnName;

    private List<ProjectionDto> children = Collections.emptyList();

    public ProjectionDto(String columnName) {
        this.columnName = columnName;
    }
}
