package com.iscweb.service;

import com.iscweb.common.annotations.CompositeCacheEntry;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.RegionDto;
import com.iscweb.common.model.dto.entity.core.RegionSearchResultDto;
import com.iscweb.common.model.entity.IRegion;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.RegionEntityService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;
import static com.iscweb.common.security.ApplicationSecurity.PERMIT_ALL;

@Slf4j
@Service
public class RegionService extends BaseBusinessService<RegionDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RegionEntityService entityService;

    @CompositeCacheEntry(CompositeCacheEntry.UpdateType.CREATE)
    public RegionDto create(RegionDto dto) throws ServiceException {
        return super.create(dto);
    }

    @CompositeCacheEntry(CompositeCacheEntry.UpdateType.UPDATE)
    public RegionDto update(RegionDto dto) throws ServiceException {
        return super.update(dto);
    }

    @CompositeCacheEntry(CompositeCacheEntry.UpdateType.DELETE)
    public void delete(String guid) {
        super.delete(guid);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public IRegion findByGuid(String guid) {
        return getEntityService().findByGuid(guid);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public List<RegionDto> findAll(PageRequest pageRequest) {
        return getEntityService().findAll(pageRequest)
                .stream()
                .map(jpa -> (RegionDto) Convert.my(jpa)
                        .scope(Scope.ALL)
                        .boom())
                .collect(Collectors.toList());
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public RegionSearchResultDto findRegions(QueryFilterDto filter, PageRequest pageRequest) {
        return (RegionSearchResultDto) getEntityService().findRegions(filter, pageRequest);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public RegionDto findByName(String regionName) {
        RegionDto result = null;

        IRegion region = getEntityService().findByName(regionName)
                .stream()
                .filter(r -> r.getName().equalsIgnoreCase(regionName))
                .findFirst()
                .orElse(null);
        if (region != null) {
            result = Convert.my(region).boom();
        }

        return result;
    }
}
