package com.iscweb.service;

import com.google.common.collect.Sets;
import com.iscweb.common.annotations.CompositeCacheEntry;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.dto.entity.core.DoorSearchResultDto;
import com.iscweb.common.model.entity.IDoor;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.DoorEntityService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

@Slf4j
@Service
public class DoorService extends BaseDeviceBusinessService<DoorDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DoorEntityService entityService;

    @PreAuthorize(IS_AUTHENTICATED)
    public DoorDto findByGuid(String guid, List<LazyLoadingField> fields) {
        final IDoor entity = getEntityService().findByGuid(guid);
        return entity != null ? Convert.my(entity).scope(Scope.fromLazyField(fields)).boom() : null;
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public List<DoorDto> findAll(PageRequest paging) {
        return getEntityService().findAll(paging)
                                 .stream()
                                 .map(jpa -> (DoorDto) Convert.my(jpa)
                                                              .scope(Scope.ALL)
                                                              .boom())
                                 .collect(Collectors.toList());
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public DoorSearchResultDto findDoors(QueryFilterDto filter, PageRequest pageRequest) {
        return (DoorSearchResultDto) getEntityService().findDoors(filter, pageRequest);
    }

    /**
     * Used for updating a single door dto parents based on door naming pattern.
     * @param door object to update.
     */
    @CompositeCacheEntry(CompositeCacheEntry.UpdateType.UPDATE)
    public DoorDto updateDoorParents(DoorDto door) {
        DoorDto result = door;
        try {
            log.info("resolving parent for door: " + door.getName());
            door.setParentIds(Sets.newHashSet(resolveParentRegion(EntityType.DOOR, door.getName())));
            result = getEntityService().update(door, null);
        } catch (Exception e) {
            log.error(e.getCause() != null ? e.getCause().toString() : e.toString());
        }

        return result;
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public List<DoorDto> findByGuidIn(Set<String> parentIds) {
        return getEntityService().findByGuidIn(parentIds);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public IDoor findByExternalId(String externalId, List<LazyLoadingField> deviceState) {
        return getEntityService().findByExternalId(externalId, deviceState);
    }
}
