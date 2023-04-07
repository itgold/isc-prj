package com.iscweb.common.model.dto.response;

import com.iscweb.common.model.dto.IDto;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Data
public class PageResponseDto<T extends IDto> implements IDto {
    private List<T> data;
    private int numberOfItems;
    private int numberOfPages;
}
