package com.iscweb.component.web.controller.graphql.queries;

import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.TagDto;
import com.iscweb.common.model.dto.entity.core.TagSearchResultDto;
import com.iscweb.common.security.ApplicationSecurity;
import com.iscweb.component.web.controller.graphql.common.PageRequestDto;
import com.iscweb.component.web.controller.graphql.common.SortOrderDto;
import com.iscweb.component.web.util.GraphQlUtils;
import com.iscweb.service.TagService;
import com.iscweb.service.security.IscPrincipal;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.iscweb.component.web.util.GraphQlUtils.sortingPage;

/**
 * GraphQL query resolver for tags.
 */
@Slf4j
@Component
public class GraphQlTagsQuery implements GraphQLQueryResolver {

    private static final PageRequest DEFAULT_PAGE = PageRequest.of(0, 10, Sort.unsorted());

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagService tagsService;

    public List<TagDto> tags(DataFetchingEnvironment env) {
        return tags(null, null, env);
    }

    public List<TagDto> tags(PageRequestDto page, List<SortOrderDto> sort, DataFetchingEnvironment env) {
        IscPrincipal principal = GraphQlUtils.currentUser(env);
        log.debug("Executed by user '{}', roles: {}, is admin: {}",
                principal.getUsername(),
                StringUtils.join(GraphQlUtils.currentUserRoles(env), ", "),
                GraphQlUtils.hasRole(ApplicationSecurity.ADMINISTRATORS_PERMISSION, env));

        List<ProjectionDto> columns = GraphQlUtils.queryFieldsSelection(env);
        log.debug("Query field '{}', properties: {}",
                env.getField().getName(),
                StringUtils.join(columns, ", "));

        return getTagsService().findAll(columns, sortingPage(page, sort, DEFAULT_PAGE));
    }

    public TagSearchResultDto queryTags(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return getTagsService().findTags(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }
}
