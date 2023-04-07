package com.iscweb.component.web.controller.graphql.queries;

import com.iscweb.common.model.dto.entity.core.DroneDto;
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
 * GraphQL query object for drone tags.
 */
@Slf4j
@Component
public class GraphQlDroneTagsQuery implements GraphQLResolver<DroneDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagService tagsService;

    public List<TagDto> getTags(DroneDto entity) {
        return tagsService.findByDroneGuid(entity.getId());
    }
}
