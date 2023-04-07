package com.iscweb.component.web.controller.graphql.queries;

import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.DistrictSearchResultDto;
import com.iscweb.common.model.dto.entity.core.SchoolDistrictDto;
import com.iscweb.common.model.dto.entity.core.SchoolDto;
import com.iscweb.common.model.dto.entity.core.SchoolSearchResultDto;
import com.iscweb.common.security.ApplicationSecurity;
import com.iscweb.component.web.controller.graphql.common.PageRequestDto;
import com.iscweb.component.web.controller.graphql.common.SortOrderDto;
import com.iscweb.component.web.util.GraphQlUtils;
import com.iscweb.service.SchoolDistrictService;
import com.iscweb.service.SchoolService;
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
 * GraphQL query resolver for schools.
 */
@Slf4j
@Component
public class GraphQlSchoolQuery implements GraphQLQueryResolver {

    private static final PageRequest DEFAULT_PAGE = PageRequest.of(0, 10, Sort.unsorted());

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SchoolService schoolService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SchoolDistrictService schoolDistrictService;

    public List<SchoolDto> schools(DataFetchingEnvironment env) {
        return schools(null, null, env);
    }

    public List<SchoolDto> schools(PageRequestDto page, List<SortOrderDto> sort, DataFetchingEnvironment env) {
        IscPrincipal principal = GraphQlUtils.currentUser(env);
        log.debug("Executed by user '" + principal.getUsername() + ", roles: "
                + StringUtils.join(GraphQlUtils.currentUserRoles(env), ", ") + ", is admin: "
                + GraphQlUtils.hasRole(ApplicationSecurity.ADMINISTRATORS_PERMISSION, env));

        List<ProjectionDto> columns = GraphQlUtils.queryFieldsSelection(env);
        log.debug("Query field '" + env.getField().getName() + ", properties: "
                + StringUtils.join(columns, ", "));

        return getSchoolService().findAll(columns, sortingPage(page, sort, DEFAULT_PAGE));
    }
    public SchoolSearchResultDto querySchools(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return getSchoolService().findSchools(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }

    public List<SchoolDistrictDto> districts(DataFetchingEnvironment env) {
        return districts(null, null, env);
    }

    public List<SchoolDistrictDto> districts(PageRequestDto page, List<SortOrderDto> sort, DataFetchingEnvironment env) {
        List<ProjectionDto> columns = GraphQlUtils.queryFieldsSelection(env);
        return getSchoolDistrictService().findAll(columns, sortingPage(page, sort, DEFAULT_PAGE));
    }

    public DistrictSearchResultDto queryDistricts(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return getSchoolDistrictService().findDistricts(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }
}
