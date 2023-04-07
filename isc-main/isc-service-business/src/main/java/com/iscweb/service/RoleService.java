package com.iscweb.service;

import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.RoleDto;
import com.iscweb.common.model.dto.entity.core.RoleSearchResultDto;
import com.iscweb.service.entity.RoleEntityService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

@Service
public class RoleService extends BaseBusinessService<RoleDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RoleEntityService entityService;

    @PreAuthorize(IS_AUTHENTICATED)
    public List<RoleDto> findAll(List<ProjectionDto> columns, PageRequest pageRequest) {
        return getEntityService().findAll(columns, pageRequest);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public RoleSearchResultDto findRoles(QueryFilterDto filter, PageRequest pageRequest) {
        return (RoleSearchResultDto) getEntityService().findRoles(filter, pageRequest);
    }
}
