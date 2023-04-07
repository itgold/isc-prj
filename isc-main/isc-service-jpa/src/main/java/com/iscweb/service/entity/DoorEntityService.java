package com.iscweb.service.entity;

import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.dto.entity.core.DoorSearchResultDto;
import com.iscweb.common.model.dto.entity.core.TagDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.entity.IDoor;
import com.iscweb.persistence.model.jpa.DeviceStateItemJpa;
import com.iscweb.persistence.model.jpa.DoorJpa;
import com.iscweb.persistence.model.jpa.DoorTagJpa;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.repositories.impl.DoorJpaRepository;
import com.iscweb.persistence.repositories.impl.DoorTagJpaRepository;
import com.iscweb.persistence.repositories.impl.TagJpaRepository;
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
 * Service class for doors.
 * <p>
 * TODO(?): Change service code to start using Resilience4j library to support retry and fallback for third party integration API calls.
 * Example of the circuit breaker usage:
 * https://www.hascode.com/2017/02/resilient-architecture-circuit-breakers-for-java-hystrix-vert-x-javaslang-and-failsafe-examples/
 * The library documentation: https://github.com/resilience4j/resilience4j#spring-boot
 *
 * @author skurenkov
 */
@Slf4j
@Service
public class DoorEntityService extends BaseRegionEntityService<DoorJpaRepository, IDoor> implements IDeviceEntityService<DoorDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DoorTagJpaRepository doorTagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagJpaRepository tagJpaRepository;

    public DoorDto create(DoorDto doorDto, List<TagDto> tags) {
        DoorJpa result = Convert.my(doorDto)
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

    @CacheEvict(value = "doors", key = "#p0.externalId", beforeInvocation = true)
    public DoorDto update(DoorDto doorDto, List<TagDto> tags) {
        DoorJpa result = Convert.my(doorDto)
                                .withJpa(findByGuid(doorDto.getId()))
                                .scope(Scope.ALL)
                                .boom();

        if (!CollectionUtils.isEmpty(doorDto.getState())) {
            Set<DeviceStateItemJpa> state = doorDto.getState()
                                                   .stream()
                                                   .map(stateItem -> new DeviceStateItemJpa(stateItem.getType(), stateItem.getValue(), stateItem.getUpdated()))
                                                   .collect(Collectors.toSet());
            result.getState().clear();
            result.getState().addAll(state);
        }

        result = (DoorJpa) update(result);
        if (!CollectionUtils.isEmpty(tags)) {
            updateTags(result, tags);
        }

        return Convert.my(result).scope(Scope.ALL).boom();
    }

    private IDoor update(IDoor door) {
        DoorJpa result = (DoorJpa) door;
        result = this.createOrUpdate(result);

        return result;
    }

    @Override
    @CacheEvict(value = "doors", allEntries = true, beforeInvocation = true)
    public void delete(String guid) {
        DoorJpa door = (DoorJpa) findByGuid(guid);
        getDoorTagJpaRepository().deleteByEntity(door);
        super.delete(guid);
    }

    public IDoor findByGuid(String guid) {
        return getRepository().findByGuid(guid);
    }

    @Cacheable(cacheNames = "doors", key = "#p0")
    public IDoor findByExternalId(String externalId, List<LazyLoadingField> fields) {
        IDoor result = getRepository().findByExternalId(externalId);
        if (result != null && !CollectionUtils.isEmpty(fields)) {
            if (fields.contains(LazyLoadingField.PARENT_REGION) && result.getRegions() != null) {
                result.getRegions().size();
            }
            if (fields.contains(LazyLoadingField.DEVICE_STATE) && result.getState() != null) {
                result.getState().size();
            }
        }

        return result;
    }

    public List<DoorDto> findAll(List<ProjectionDto> columns, PageRequest paging) {
        return getRepository().findAll(columns, paging)
                              .stream()
                              .map(door -> (DoorDto) Convert.my(door).boom())
                              .collect(Collectors.toList());
    }

    public DoorDto getDoorDtoByGuid(String guid) {
        DoorDto out = null;
        IDoor door = getRepository().findByGuid(guid);
        if (null != door) {
            out = Convert.my(door).scope(Scope.ALL).boom();
        }
        return out;
    }

    public Set<IDoor> findByRegions(Set<RegionJpa> regions, List<LazyLoadingField> fields) {
        Set<IDoor> result = getRepository().findByRegionsIn(regions);

        if (!CollectionUtils.isEmpty(result) && !CollectionUtils.isEmpty(fields)) {
            result.forEach(device -> {
                if (fields.contains(LazyLoadingField.PARENT_REGION) && device.getRegions() != null) {
                    device.getRegions().size();
                }
                if (fields.contains(LazyLoadingField.DEVICE_STATE) && device.getState() != null) {
                    device.getState().size();
                }
            });
        }

        return result;
    }

    public PageResponseDto<DoorDto> findDoors(QueryFilterDto filter, Pageable paging) {
        Page<IDoor> page = getRepository().findEntities(filter, paging);
        List<DoorDto> pageData = page.getContent()
                                     .stream()
                                     .map(door -> (DoorDto) Convert.my(door).scope(Scope.ALL).boom())
                                     .collect(Collectors.toList());
        return DoorSearchResultDto.builder()
                                  .numberOfItems((int) page.getTotalElements())
                                  .numberOfPages(page.getTotalPages())
                                  .data(pageData).build();
    }

    private void updateTags(DoorJpa door, List<TagDto> tags) {
        if (tags != null && door != null) {
            List<DoorTagJpa> existingTags = getDoorTagJpaRepository().findAllByEntityGuid(door.getGuid());
            List<DoorTagJpa> toDelete = existingTags
                    .stream()
                    .filter(tag -> tags.stream().filter(t -> StringUtils.equals(t.getId(), tag.getGuid())).findFirst().orElse(null) == null)
                    .collect(Collectors.toList());
            toDelete.forEach(tag -> getDoorTagJpaRepository().delete(tag));

            List<TagDto> toAdd = tags
                    .stream()
                    .filter(tag -> existingTags.stream().filter(t -> StringUtils.equals(t.getGuid(), tag.getId())).findFirst().orElse(null) == null)
                    .collect(Collectors.toList());
            toAdd.forEach(tagDto -> {
                TagJpa tag = getTagJpaRepository().findByGuid(tagDto.getId());
                if (tag != null) {
                    DoorTagJpa doorTag = new DoorTagJpa();
                    doorTag.setGuid(UUID.randomUUID().toString());
                    doorTag.setEntity(door);
                    doorTag.setTag(tag);
                    getDoorTagJpaRepository().save(doorTag);
                }
            });
        }
    }

    public List<DoorDto> findByGuidIn(Set<String> parentIds) {
        return getRepository().findByGuidIn(parentIds)
                .stream()
                .map(door -> (DoorDto) Convert.my(door).boom())
                .collect(Collectors.toList());
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
