package com.iscweb.component.web.controller.graphql.queries;

import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.ExternalUserSearchResultDto;
import com.iscweb.common.model.dto.entity.core.RoleDto;
import com.iscweb.common.model.dto.entity.core.RoleSearchResultDto;
import com.iscweb.common.model.dto.entity.core.UserDto;
import com.iscweb.common.model.dto.entity.core.UserSearchResultDto;
import com.iscweb.component.web.controller.graphql.common.PageRequestDto;
import com.iscweb.component.web.controller.graphql.common.SortOrderDto;
import com.iscweb.component.web.util.GraphQlUtils;
import com.iscweb.service.RoleService;
import com.iscweb.service.ExternalUserService;
import com.iscweb.service.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.iscweb.component.web.util.GraphQlUtils.sortingPage;

/**
 * GraphQL query resolver for users.
 */
@Slf4j
@Component
public class GraphQlUserQuery implements GraphQLQueryResolver {

    private static final PageRequest DEFAULT_PAGE = PageRequest.of(0, 10, Sort.unsorted());

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private UserService userService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ExternalUserService externalUserService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RoleService roleService;

    public List<UserDto> users(DataFetchingEnvironment env) {
        return users(null, null, env);
    }

    public List<UserDto> users(PageRequestDto page, List<SortOrderDto> sort, DataFetchingEnvironment env) {
        return getUserService().findAll(sortingPage(page, sort, DEFAULT_PAGE));
    }
    public UserSearchResultDto queryUsers(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return getUserService().findUsers(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }

    public List<RoleDto> roles(DataFetchingEnvironment env) {
        return roles(null, null, env);
    }

    public List<RoleDto> roles(PageRequestDto page, List<SortOrderDto> sort, DataFetchingEnvironment env) {
        List<ProjectionDto> columns = GraphQlUtils.queryFieldsSelection(env);
        return getRoleService().findAll(columns, sortingPage(page, sort, DEFAULT_PAGE));
    }
    public RoleSearchResultDto queryRoles(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return getRoleService().findRoles(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }

    public ExternalUserSearchResultDto queryExternalUsers(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return getExternalUserService().findUsers(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }
}
