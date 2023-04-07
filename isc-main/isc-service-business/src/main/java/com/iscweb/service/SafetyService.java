package com.iscweb.service;

import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.SafetyDto;
import com.iscweb.common.model.dto.entity.core.SafetySearchResultDto;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.SafetyEntityService;
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
public class SafetyService extends BaseDeviceBusinessService<SafetyDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SafetyEntityService entityService;

    @PreAuthorize(IS_AUTHENTICATED)
    public List<SafetyDto> findAll(PageRequest paging) {
        return getEntityService().findAll(paging)
                .stream()
                .map(jpa -> (SafetyDto) Convert.my(jpa)
                        .scope(Scope.ALL)
                        .boom())
                .collect(Collectors.toList());
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public SafetySearchResultDto findSafeties(QueryFilterDto filter, PageRequest pageRequest) {
        return (SafetySearchResultDto) getEntityService().findSafeties(filter, pageRequest);
    }
}
