package com.iscweb.service.entity;

import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.SafetyDto;
import com.iscweb.common.model.dto.entity.core.SafetySearchResultDto;
import com.iscweb.common.model.dto.entity.core.TagDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.entity.ISafety;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.model.jpa.SafetyJpa;
import com.iscweb.persistence.model.jpa.SafetyTagJpa;
import com.iscweb.persistence.repositories.impl.TagJpaRepository;
import com.iscweb.persistence.repositories.impl.SafetyJpaRepository;
import com.iscweb.persistence.repositories.impl.SafetyTagJpaRepository;
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
 * Service class for safeties.
 * @author arezen
 */
@Slf4j
@Service
public class SafetyEntityService extends BaseRegionEntityService<SafetyJpaRepository, ISafety> implements IDeviceEntityService<SafetyDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SafetyTagJpaRepository safetyTagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagJpaRepository tagJpaRepository;

    public SafetyDto create(SafetyDto safetyDto, List<TagDto> tags) {
        SafetyJpa result = Convert.my(safetyDto)
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

    @CacheEvict(value = "safeties", key = "#p0.externalId", beforeInvocation = true)
    public SafetyDto update(SafetyDto safetyDto, List<TagDto> tags) {
        SafetyJpa result = Convert.my(safetyDto)
                                  .withJpa(findByGuid(safetyDto.getId()))
                                  .scope(Scope.ALL)
                                  .boom();

        result = (SafetyJpa) update(result);
        if (!CollectionUtils.isEmpty(tags)) {
            updateTags(result, tags);
        }

        return Convert.my(result).scope(Scope.ALL).boom();
    }

    public ISafety update(ISafety safety) {
        SafetyJpa result = (SafetyJpa) safety;
        result = this.createOrUpdate(result);

        return result;
    }

    @Override
    @CacheEvict(value = "safeties", allEntries = true, beforeInvocation = true)
    public void delete(String guid) {
        SafetyJpa safety = (SafetyJpa) findByGuid(guid);
        getSafetyTagJpaRepository().deleteByEntity(safety);
        super.delete(guid);
    }

    public ISafety findByGuid(String guid) {
        return getRepository().findByGuid(guid);
    }

    @Cacheable(cacheNames = "safeties", key = "#p0")
    public ISafety findByExternalId(String externalId, List<LazyLoadingField> fields) {
        ISafety result = getRepository().findByExternalId(externalId);
        if (result != null && !CollectionUtils.isEmpty(fields)) {
            if (fields.contains(LazyLoadingField.PARENT_REGION) && result.getRegions() != null) {
                result.getRegions().size();
            }
        }

        return result;
    }

    public List<SafetyDto> findAll(List<ProjectionDto> columns, PageRequest paging) {
        return getRepository().findAll(columns, paging)
                              .stream()
                              .map(safety -> (SafetyDto) Convert.my(safety).boom())
                              .collect(Collectors.toList());
    }

    public SafetyDto getSafetyDtoByGuid(String guid) {
        SafetyDto out = null;
        ISafety safety = getRepository().findByGuid(guid);
        if (null != safety) {
            out = Convert.my(safety).scope(Scope.ALL).boom();
        }
        return out;
    }

    public Set<ISafety> findByRegions(Set<RegionJpa> regions, List<LazyLoadingField> fields) {
        Set<ISafety> result = getRepository().findByRegionsIn(regions);

        if (!CollectionUtils.isEmpty(result) && !CollectionUtils.isEmpty(fields)) {
            result.forEach(device -> {
                if (fields.contains(LazyLoadingField.PARENT_REGION) && device.getRegions() != null) {
                    device.getRegions().size();
                }
            });
        }

        return result;
    }

    public PageResponseDto<SafetyDto> findSafeties(QueryFilterDto filter, Pageable paging) {
        Page<ISafety> page = getRepository().findEntities(filter, paging);
        List<SafetyDto> pageData = page.getContent()
                .stream()
                .map(safety -> (SafetyDto) Convert.my(safety).scope(Scope.ALL).boom())
                .collect(Collectors.toList());
        return SafetySearchResultDto.builder()
                .numberOfItems((int) page.getTotalElements())
                .numberOfPages(page.getTotalPages())
                .data(pageData).build();
    }

    private void updateTags(SafetyJpa safety, List<TagDto> tags) {
        if (tags != null && safety != null) {
            List<SafetyTagJpa> existingTags = getSafetyTagJpaRepository().findAllByEntityGuid(safety.getGuid());
            List<SafetyTagJpa> toDelete = existingTags
                    .stream()
                    .filter(tag -> tags.stream().filter(t -> StringUtils.equals(t.getId(), tag.getGuid())).findFirst().orElse(null) == null)
                    .collect(Collectors.toList());
            toDelete.forEach(tag -> getSafetyTagJpaRepository().delete(tag));

            List<TagDto> toAdd = tags
                    .stream()
                    .filter(tag -> existingTags.stream().filter(t -> StringUtils.equals(t.getGuid(), tag.getId())).findFirst().orElse(null) == null)
                    .collect(Collectors.toList());
            toAdd.forEach(tagDto -> {
                TagJpa tag = getTagJpaRepository().findByGuid(tagDto.getId());
                if (tag != null) {
                    SafetyTagJpa safetyTag = new SafetyTagJpa();
                    safetyTag.setGuid(UUID.randomUUID().toString());
                    safetyTag.setEntity(safety);
                    safetyTag.setTag(tag);
                    getSafetyTagJpaRepository().save(safetyTag);
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
