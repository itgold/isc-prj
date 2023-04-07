package com.iscweb.component.web.controller.graphql.queries;

import com.iscweb.common.model.dto.entity.core.SafetyDto;
import com.iscweb.common.model.dto.entity.core.TagDto;
import com.iscweb.service.TagService;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * GraphQL query object for safety tags.
 */
@Slf4j
@Component
public class GraphQlSafetyTagsQuery implements GraphQLResolver<SafetyDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagService tagsService;

    public List<TagDto> getTags(SafetyDto entity) {
        return tagsService.findBySafetyGuid(entity.getId());
    }
}
