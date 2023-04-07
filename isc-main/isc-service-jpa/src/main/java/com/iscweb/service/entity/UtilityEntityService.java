package com.iscweb.service.entity;

import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.TagDto;
import com.iscweb.common.model.dto.entity.core.UtilityDto;
import com.iscweb.common.model.dto.entity.core.UtilitySearchResultDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.entity.IUtility;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.model.jpa.UtilityJpa;
import com.iscweb.persistence.model.jpa.UtilityTagJpa;
import com.iscweb.persistence.repositories.impl.TagJpaRepository;
import com.iscweb.persistence.repositories.impl.UtilityJpaRepository;
import com.iscweb.persistence.repositories.impl.UtilityTagJpaRepository;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.iscweb.service.converter.Convert.GUID;

/**
 * Service class for utilities.
 * @author arezen
 */
@Slf4j
@Service
public class UtilityEntityService extends BaseRegionEntityService<UtilityJpaRepository, IUtility> implements IDeviceEntityService<UtilityDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private UtilityTagJpaRepository utilityTagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagJpaRepository tagJpaRepository;

    public UtilityDto create(UtilityDto utilityDto, List<TagDto> tags) {
        UtilityJpa result = Convert.my(utilityDto)
                .scope(Scope.ALL)
                .attr(GUID, true)
                .boom();

        if (StringUtils.isEmpty(result.getGuid())) {
            result.setGuid(UUID.randomUUID().toString());
        }

        result = this.createOrUpdate(result);
        if (!CollectionUtils.isEmpty(tags)) {
            updateTags(result, tags);
        }

        return Convert.my(result).scope(Scope.ALL).boom();
    }

    @CacheEvict(value = "utilities", key = "#p0.externalId", beforeInvocation = true)
    public UtilityDto update(UtilityDto utilityDto, List<TagDto> tags) {
        UtilityJpa result = Convert.my(utilityDto)
                .withJpa(findByGuid(utilityDto.getId()))
                .scope(Scope.ALL)
                .boom();

        result = (UtilityJpa) update(result);
        if (!CollectionUtils.isEmpty(tags)) {
            updateTags(result, tags);
        }

        return Convert.my(result).scope(Scope.ALL).boom();
    }

    public IUtility update(IUtility utility) {
        UtilityJpa result = (UtilityJpa) utility;
        result = this.createOrUpdate(result);

        return result;
    }

    @Override
    @CacheEvict(value = "utilities", allEntries = true, beforeInvocation = true)
    public void delete(String guid) {
        UtilityJpa utility = (UtilityJpa) findByGuid(guid);
        getUtilityTagJpaRepository().deleteByEntity(utility);
        super.delete(guid);
    }

    public IUtility findByGuid(String guid) {
        return getRepository().findByGuid(guid);
    }

    @Cacheable(cacheNames = "utilities", key = "#p0")
    public IUtility findByExternalId(String externalId, List<LazyLoadingField> fields) {
        IUtility result = getRepository().findByExternalId(externalId);
        if (result != null && !CollectionUtils.isEmpty(fields)) {
            if (fields.contains(LazyLoadingField.PARENT_REGION) && result.getRegions() != null) {
                result.getRegions().size();
            }
        }

        return result;
    }

    public List<UtilityDto> findAll(List<ProjectionDto> columns, PageRequest paging) {
        return getRepository().findAll(columns, paging)
                .stream()
                .map(utility -> (UtilityDto) Convert.my(utility).boom())
                .collect(Collectors.toList());
    }

    public UtilityDto getUtilityDtoByGuid(String guid) {
        UtilityDto out = null;
        IUtility utility = getRepository().findByGuid(guid);
        if (null != utility) {
            out = Convert.my(utility).scope(Scope.ALL).boom();
        }
        return out;
    }

    public Set<IUtility> findByRegions(Set<RegionJpa> regions, List<LazyLoadingField> fields) {
        Set<IUtility> result = getRepository().findByRegionsIn(regions);

        if (!CollectionUtils.isEmpty(result) && !CollectionUtils.isEmpty(fields)) {
            result.forEach(device -> {
                if (fields.contains(LazyLoadingField.PARENT_REGION) && device.getRegions() != null) {
                    device.getRegions().size();
                }
            });
        }

        return result;
    }

    public PageResponseDto<UtilityDto> findUtilities(QueryFilterDto filter, Pageable paging) {
        Page<IUtility> page = getRepository().findEntities(filter, paging);
        List<UtilityDto> pageData = page.getContent()
                .stream()
                .map(utility -> (UtilityDto) Convert.my(utility).scope(Scope.ALL).boom())
                .collect(Collectors.toList());
        return UtilitySearchResultDto.builder()
                .numberOfItems((int) page.getTotalElements())
                .numberOfPages(page.getTotalPages())
                .data(pageData).build();
    }

    private void updateTags(UtilityJpa utility, List<TagDto> tags) {
        if (tags != null && utility != null) {
            List<UtilityTagJpa> existingTags = getUtilityTagJpaRepository().findAllByEntityGuid(utility.getGuid());
            List<UtilityTagJpa> toDelete = existingTags
                    .stream()
                    .filter(tag -> tags.stream().filter(t -> StringUtils.equals(t.getId(), tag.getGuid())).findFirst().orElse(null) == null)
                    .collect(Collectors.toList());
            toDelete.forEach(tag -> getUtilityTagJpaRepository().delete(tag));

            List<TagDto> toAdd = tags
                    .stream()
                    .filter(tag -> existingTags.stream().filter(t -> StringUtils.equals(t.getGuid(), tag.getId())).findFirst().orElse(null) == null)
                    .collect(Collectors.toList());
            toAdd.forEach(tagDto -> {
                TagJpa tag = getTagJpaRepository().findByGuid(tagDto.getId());
                if (tag != null) {
                    UtilityTagJpa utilityTag = new UtilityTagJpa();
                    utilityTag.setGuid(UUID.randomUUID().toString());
                    utilityTag.setEntity(utility);
                    utilityTag.setTag(tag);
                    getUtilityTagJpaRepository().save(utilityTag);
                }
            });
        }
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */

    @Override
    public Logger getLogger() {
        return log;
    }

}
