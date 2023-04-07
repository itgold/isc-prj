package com.iscweb.common.model.dto.entity.core;

import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.event.IncrementalUpdateEventDto;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class IncrementalEventsSearchResultDto extends PageResponseDto<IncrementalUpdateEventDto> {
}
