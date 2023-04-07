package com.iscweb.service;

import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.DistrictSearchResultDto;
import com.iscweb.common.model.dto.entity.core.SchoolDistrictDto;
import com.iscweb.service.entity.SchoolDistrictEntityService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;
import static com.iscweb.common.security.ApplicationSecurity.PERMIT_ALL;

@Service
public class SchoolDistrictService extends BaseBusinessService<SchoolDistrictDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SchoolDistrictEntityService entityService;

    @PreAuthorize(IS_AUTHENTICATED)
    public List<SchoolDistrictDto> findAll(List<ProjectionDto> columns, PageRequest pageRequest) {
        return getEntityService().findAll(columns, pageRequest);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public DistrictSearchResultDto findDistricts(QueryFilterDto filter, PageRequest pageRequest) {
        return (DistrictSearchResultDto) getEntityService().findDistricts(filter, pageRequest);
    }
}
