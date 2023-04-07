package com.iscweb.component.web.controller.graphql.mutations;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.entity.core.TagDto;
import com.iscweb.component.web.controller.graphql.common.UpdateResultDto;
import com.iscweb.component.web.controller.graphql.common.UpdateResultDto.Status;
import com.iscweb.service.TagService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GraphQL mutation for tags.
 */
@Component
public class GraphQlTagMutation implements GraphQLMutationResolver {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagService tagsService;

    public TagDto newTag(TagDto tagDto) throws ServiceException {
        return getTagsService().create(tagDto);
    }

    public TagDto updateTag(TagDto tagDto) throws ServiceException {
        return getTagsService().update(tagDto);
    }

    public UpdateResultDto deleteTag(String tagId) {
        getTagsService().delete(tagId);
        return new UpdateResultDto(Status.SUCCESS.name(), tagId);
    }
}
