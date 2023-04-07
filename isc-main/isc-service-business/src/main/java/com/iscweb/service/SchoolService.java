package com.iscweb.service;

import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.SchoolDto;
import com.iscweb.common.model.dto.entity.core.SchoolSearchResultDto;
import com.iscweb.service.entity.SchoolEntityService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

@Service
public class SchoolService extends BaseBusinessService<SchoolDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SchoolEntityService entityService;

    @PreAuthorize(IS_AUTHENTICATED)
    public List<SchoolDto> findAll(List<ProjectionDto> columns, PageRequest pageRequest) {
        return getEntityService().findAll(columns, pageRequest);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public SchoolSearchResultDto findSchools(QueryFilterDto filter, PageRequest pageRequest) {
        return (SchoolSearchResultDto) getEntityService().findSchools(filter, pageRequest);
    }
}
