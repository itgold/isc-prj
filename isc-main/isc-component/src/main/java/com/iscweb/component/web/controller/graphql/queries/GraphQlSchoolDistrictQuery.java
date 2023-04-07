package com.iscweb.component.web.controller.graphql.queries;

import com.iscweb.common.model.dto.entity.core.SchoolDistrictDto;
import com.iscweb.common.model.dto.entity.core.SchoolDto;
import com.iscweb.component.web.util.GraphQlUtils;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * GraphQL resolver object for school district objects by ISchool instance.
 */
@Slf4j
@Component
public class GraphQlSchoolDistrictQuery implements GraphQLResolver<SchoolDto> {

    /**
     * Primary method to resolve school district by school.
     *
     * @param school ISchool object instance.
     * @param env data fetching environment object reference.
     * @return a new instance of a school district object.
     */
    public SchoolDistrictDto getDistrict(SchoolDto school, DataFetchingEnvironment env) {
        log.trace("Query field '" + env.getField().getName() + ", properties: "
                + StringUtils.join(GraphQlUtils.queryFieldsSelection(env), ","));

        return school.getSchoolDistrict();
    }
}
