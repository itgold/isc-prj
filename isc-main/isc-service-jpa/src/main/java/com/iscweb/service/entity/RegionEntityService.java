package com.iscweb.service.entity;

import com.google.common.collect.Sets;
import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.RegionDto;
import com.iscweb.common.model.dto.entity.core.RegionSearchResultDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.entity.IRegion;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.repositories.impl.RegionJpaRepository;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.iscweb.common.util.ObjectUtils.ID_NONE;
import static com.iscweb.service.converter.Convert.GUID;

/**
 * Service class for regions.
 *
 * @author skurenkov
 */
@Slf4j
@Service
public class RegionEntityService extends BaseRegionEntityService<RegionJpaRepository, IRegion> implements EntityService<RegionDto> {

    public RegionDto create(RegionDto regionDto) {
        RegionJpa result = Convert.my(regionDto)
                .scope(Scope.ALL)
                .attr(GUID, true)
                .boom();

        result = this.createOrUpdate(result);

        return Convert.my(result).scope(Scope.ALL).boom();
    }

    @CacheEvict(value = "regions", key = "#p0.id", beforeInvocation = true)
    public RegionDto update(RegionDto regionDto) {

        RegionJpa result = Convert.my(regionDto)
                .withJpa(findByGuid(regionDto.getId(), Arrays.asList(LazyLoadingField.PARENT_REGION, LazyLoadingField.METADATA)))
                .scope(Scope.ALL)
                .boom();

        return Convert.my(updateRegion(result)).scope(Scope.ALL).boom();
    }

    @CacheEvict(value = "regions", key = "#p0.guid", beforeInvocation = true)
    public RegionDto update(RegionJpa region) {
        return Convert.my(updateRegion(region)).scope(Scope.ALL).boom();
    }

    @Override
    @CacheEvict(value = "regions", key = "#p0", beforeInvocation = true)
    public void delete(String guid) {
        RegionJpa region = (RegionJpa) findByGuid(guid);
        if (region != null) {
            super.delete(guid);
        }
    }

    @Cacheable(cacheNames = "regions", key = "#p0")
    public IRegion findByGuid(String guid) {
        return findByGuid(guid, null);
    }

    /**
     * Looks up a region with a specific guid.
     *
     * @param guid identifier to search by.
     * @return found instance or null.
     */
    public IRegion findByGuid(String guid, List<LazyLoadingField> fields) {
        RegionJpa result = null;
        if (guid != null) {
            result = getRepository().findByGuid(guid);

            // load lazy collections
            if (result != null && !CollectionUtils.isEmpty(fields)) {
                if (fields.contains(LazyLoadingField.PARENT_REGION) && result.getRegions() != null) {
                    //noinspection ResultOfMethodCallIgnored
                    result.getRegions().size();
                }
                if (fields.contains(LazyLoadingField.METADATA) && result.getProps() != null) {
                    result.getProps().size();
                }
            }
        }
        return result;
    }

    public List<RegionDto> findAll(List<ProjectionDto> columns, PageRequest paging) {
        return getRepository().findAll(columns, paging)
                              .stream()
                              .map(region -> (RegionDto) Convert.my(region).boom())
                              .collect(Collectors.toList());
    }

    public Set<RegionJpa> findByName(String regionName) {
        return getRepository().findByNameStartingWith(regionName);
    }

    public Set<RegionJpa> findByName(String regionName, RegionJpa parent) {
        return getRepository().findByNameStartingWithAndMemberOfRegions(regionName, parent);
    }

    /**
     * Used to find default root region of all regions in the system. It is declared by the ID_NONE identifier.
     *
     * @return global parent region.
     */
    public IRegion fetchGlobalRootRegion() {
        return findById(ID_NONE);
    }

    public PageResponseDto<RegionDto> findRegions(QueryFilterDto filter, Pageable paging) {
        Page<IRegion> page = findRegionEntities(filter, paging);

        return RegionSearchResultDto.builder()
                                    .numberOfItems((int) page.getTotalElements())
                                    .numberOfPages(page.getTotalPages())
                                    .data(page.getContent()
                                              .stream()
                                              .map(door -> (RegionDto) Convert.my(door).scope(Scope.ALL).boom())
                                              .collect(Collectors.toList())).build();
    }

    public Page<IRegion> findRegionEntities(QueryFilterDto filter, Pageable paging) {
        return getRepository().findEntities(filter, paging);
    }

    public Set<RegionJpa> findRegionsByParent(Set<String> parentGuis) {
        return getRegionRepository().findByGuidIn(parentGuis);
    }

    public Set<RegionJpa> findByRegion(RegionJpa rootRegion) {
        return findByRegion(rootRegion, null);
    }

    /**
     * Looks up children regions of a given root region.
     *
     * @param rootRegion root region to lookup.
     * @return a set of children regions.
     */
    public Set<RegionJpa> findByRegion(RegionJpa rootRegion, List<LazyLoadingField> fields) {
        Set<RegionJpa> result = getRepository().findByRegionsIn(Sets.newHashSet(rootRegion));

        // load lazy collections
        if (!CollectionUtils.isEmpty(result) && !CollectionUtils.isEmpty(fields)) {
            result.forEach(childRegion -> {
                if (fields.contains(LazyLoadingField.PARENT_REGION) && childRegion.getRegions() != null) {
                    //noinspection ResultOfMethodCallIgnored
                    childRegion.getRegions().size();
                }
                if (fields.contains(LazyLoadingField.METADATA) && childRegion.getProps() != null) {
                    childRegion.getProps().size();
                }
            });
        }

        return result;
    }

    private IRegion updateRegion(IRegion region) {

        RegionJpa result = (RegionJpa) region;
        result = this.createOrUpdate(result);

        return result;
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
