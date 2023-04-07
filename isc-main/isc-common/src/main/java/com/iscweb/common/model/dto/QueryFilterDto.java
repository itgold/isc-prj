package com.iscweb.common.model.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class QueryFilterDto implements IDto {
    private List<String> tags = Lists.newArrayList();
    private List<ColumnFilterDto> columns = Lists.newArrayList();
}
