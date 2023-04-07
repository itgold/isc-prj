package com.iscweb.component.web.controller.graphql.queries;

import com.iscweb.common.model.dto.entity.core.RadioDto;
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
 * GraphQL query object for radio tags.
 */
@Slf4j
@Component
public class GraphQlRadioTagsQuery implements GraphQLResolver<RadioDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagService tagsService;

    public List<TagDto> getTags(RadioDto entity) {
        return getTagsService().findByRadioGuid(entity.getId());
    }
}
