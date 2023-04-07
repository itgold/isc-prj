package com.iscweb.service;

import com.google.common.collect.Lists;
import com.iscweb.common.annotations.CompositeCacheEntry;
import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.RegionDto;
import com.iscweb.common.model.dto.entity.core.ZoneDto;
import com.iscweb.common.model.dto.entity.core.ZoneSearchResultDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.entity.IRegion;
import com.iscweb.common.service.IApplicationSecuredService;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.service.composite.CompositeService;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.RegionEntityService;
import com.iscweb.service.entity.SchoolEntityService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for regions.
 *
 * @author skurenkov
 */
@Slf4j
@Service
@Transactional(transactionManager = "jpaTransactionManager")
public class ZoneService implements IApplicationSecuredService {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SchoolEntityService schoolEntityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RegionEntityService regionEntityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CompositeService compositeService;

    @CompositeCacheEntry(CompositeCacheEntry.UpdateType.CREATE)
    public ZoneDto create(ZoneDto regionDto) {
        RegionDto updatedRegion = getRegionEntityService().create(regionDto);
        return convertRegionToZone(updatedRegion, updateChildRegions(updatedRegion, regionDto.getChildIds()));
    }

    @CompositeCacheEntry(CompositeCacheEntry.UpdateType.UPDATE)
    public ZoneDto update(ZoneDto regionDto) {
        RegionDto updatedRegion = getRegionEntityService().update(regionDto);
        return convertRegionToZone(updatedRegion, updateChildRegions(updatedRegion, regionDto.getChildIds()));
    }

    @CompositeCacheEntry(CompositeCacheEntry.UpdateType.DELETE)
    public void delete(String guid) {
        RegionJpa region = (RegionJpa) findByGuid(guid);
        if (region != null) {
            List<RegionJpa> removedChildren = removeParent(guid, Lists.newArrayList(getRegionEntityService().findByRegion(region)));
            removedChildren.forEach(getRegionEntityService()::update);
            getRegionEntityService().delete(region.getGuid());
        }
    }

    /**
     * Looks up a region with a specific guid.
     *
     * @param guid identifier to search by.
     * @return found instance or null.
     */
    public IRegion findByGuid(String guid) {
        return getRegionEntityService().findByGuid(guid);
    }

    public PageResponseDto<ZoneDto> findZones(QueryFilterDto filter, Pageable paging) {
        Page<IRegion> page = getRegionEntityService().findRegionEntities(filter, paging);

        return ZoneSearchResultDto.builder()
                                  .numberOfItems((int) page.getTotalElements())
                                  .numberOfPages(page.getTotalPages())
                                  .data(page.getContent()
                                            .stream()
                                            .map(region -> convertRegionToZone((RegionJpa) region))
                                            .collect(Collectors.toList())).build();
    }

    private ZoneDto convertRegionToZone(RegionJpa region) {
        ZoneDto zone = new ZoneDto(Convert.my(region).scope(Scope.ALL).boom());
        Set<RegionJpa> children = getRegionEntityService().findByRegion(region);
        if (children.size() > 0) {
            for (RegionJpa child : children) {
                zone.getChildIds().add(child.getGuid());
            }
        }

        //todo: take Region properties (color, description) and make them as direct properties of the ZoneDto.
        return zone;
    }

    private ZoneDto convertRegionToZone(RegionDto region, Collection<RegionJpa> children) {
        ZoneDto zone = new ZoneDto(region);
        if (children.size() > 0) {
            for (RegionJpa child : children) {
                zone.getChildIds().add(child.getGuid());
            }
        }

        //todo: take Region properties (color, description) and make them as direct properties of the ZoneDto.
        return zone;
    }

    private List<RegionJpa> updateChildRegions(RegionDto zoneReion, Set<String> childIds) {
        // find new children
        RegionJpa region = (RegionJpa) getRegionEntityService().findByGuid(zoneReion.getId());
        List<RegionJpa> newChildren = childIds.stream()
                .map(regionGuid -> (RegionJpa) getRegionEntityService().findByGuid(regionGuid, List.of(LazyLoadingField.PARENT_REGION)))
                .filter(childRegion -> childRegion.getRegions().stream().noneMatch(r -> zoneReion.getId().equals(r.getGuid())))
                .collect(Collectors.toList());
        List<RegionJpa> removedChildren = getRegionEntityService().findByRegion(region).stream()
                .filter(childRegion -> !childIds.contains(childRegion.getGuid()))
                .collect(Collectors.toList());

        List<RegionJpa> regionsToUpdate = Lists.newArrayList();

        log.debug("New children:");
        newChildren.forEach(child -> {
            log.debug("    CHILD: {}", child.getName());
            child.getRegions().add(region);
            regionsToUpdate.add(child);
        });

        log.debug("Removed children:");
        regionsToUpdate.addAll(removeParent(zoneReion.getId(), removedChildren));
        regionsToUpdate.forEach(childRegion -> {
            RegionDto regionDto = getRegionEntityService().update(childRegion);
            getCompositeService().refreshNode(regionDto, CompositeCacheEntry.UpdateType.UPDATE);
        });
        return newChildren;
    }

    private List<RegionJpa> removeParent(String parentRegionId, List<RegionJpa> children) {
        List<RegionJpa> regionsToUpdate = Lists.newArrayList();
        children.forEach(child -> {
            log.debug("    CHILD: {}", child.getName());
            for (Iterator<RegionJpa> iter = child.getRegions().iterator(); iter.hasNext();) {
                RegionJpa parent = iter.next();
                if (parentRegionId.equals(parent.getGuid())) {
                    iter.remove();
                    break;
                }
            }
            regionsToUpdate.add(child);
        });

        return regionsToUpdate;
    }
}
