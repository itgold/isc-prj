package com.iscweb.common.model.dto.entity.core;

import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.event.ApplicationEventDto;
import com.iscweb.common.model.event.ITypedPayload;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class ApplicationEventsSearchResultDto extends PageResponseDto<ApplicationEventsSearchResultDto.ApplicationEventSearchResultEventDto> {
    public static class ApplicationEventSearchResultEventDto extends ApplicationEventDto<ITypedPayload> {}
}
