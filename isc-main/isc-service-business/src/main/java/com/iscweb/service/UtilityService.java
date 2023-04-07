package com.iscweb.service;

import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.UtilityDto;
import com.iscweb.common.model.dto.entity.core.UtilitySearchResultDto;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.UtilityEntityService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

@Service
public class UtilityService extends BaseDeviceBusinessService<UtilityDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private UtilityEntityService entityService;

    @PreAuthorize(IS_AUTHENTICATED)
    public List<UtilityDto> findAll(PageRequest paging) {
        return getEntityService().findAll(paging)
                .stream()
                .map(jpa -> (UtilityDto) Convert.my(jpa)
                        .scope(Scope.ALL)
                        .boom())
                .collect(Collectors.toList());
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public UtilitySearchResultDto findUtilities(QueryFilterDto filter, PageRequest pageRequest) {
        return (UtilitySearchResultDto) getEntityService().findUtilities(filter, pageRequest);
    }
}
