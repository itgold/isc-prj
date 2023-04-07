package com.iscweb.component.web.controller.graphql.mutations;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.entity.core.SchoolDistrictDto;
import com.iscweb.common.model.dto.entity.core.SchoolDto;
import com.iscweb.component.web.controller.graphql.common.UpdateResultDto;
import com.iscweb.service.SchoolDistrictService;
import com.iscweb.service.SchoolService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GraphQL mutation for school objects.
 */
@Component
public class GraphQlSchoolMutation implements GraphQLMutationResolver {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SchoolService schoolService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SchoolDistrictService schoolDistrictService;

    public SchoolDto newSchool(SchoolDto school) throws ServiceException {
        return getSchoolService().create(school);
    }

    public SchoolDto updateSchool(SchoolDto school) throws ServiceException {
        return getSchoolService().update(school);
    }

    public UpdateResultDto deleteSchool(String schoolId) {
        getSchoolService().delete(schoolId);
        return new UpdateResultDto(UpdateResultDto.Status.SUCCESS.name(), schoolId);
    }

    public SchoolDistrictDto newDistrict(SchoolDistrictDto district) throws ServiceException {
        return getSchoolDistrictService().create(district);
    }

    public SchoolDistrictDto updateDistrict(SchoolDistrictDto district) throws ServiceException {
        return getSchoolDistrictService().update(district);
    }

    public UpdateResultDto deleteDistrict(String districtId) {
        getSchoolDistrictService().delete(districtId);
        return new UpdateResultDto(UpdateResultDto.Status.SUCCESS.name(), districtId);
    }
}
