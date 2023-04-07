package com.iscweb.service.entity;

import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.DroneDto;
import com.iscweb.common.model.dto.entity.core.DroneSearchResultDto;
import com.iscweb.common.model.dto.entity.core.TagDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.entity.IDrone;
import com.iscweb.persistence.model.jpa.DeviceStateItemJpa;
import com.iscweb.persistence.model.jpa.DroneJpa;
import com.iscweb.persistence.model.jpa.DroneTagJpa;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.repositories.impl.DroneJpaRepository;
import com.iscweb.persistence.repositories.impl.DroneTagJpaRepository;
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
 * Service class for drones.
 *
 * @author skurenkov
 */
@Slf4j
@Service
public class DroneEntityService extends BaseRegionEntityService<DroneJpaRepository, IDrone> implements IDeviceEntityService<DroneDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagJpaRepository tagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DroneTagJpaRepository droneTagJpaRepository;

    public DroneDto create(DroneDto droneDto, List<TagDto> tags) {
        DroneJpa result = Convert.my(droneDto)
                                 .scope(Scope.ALL)
                                 .attr(GUID, true)
                                 .boom();

        if (!CollectionUtils.isEmpty(droneDto.getState())) {
            Set<DeviceStateItemJpa> state = droneDto.getState()
                    .stream()
                    .map(stateItem -> new DeviceStateItemJpa(stateItem.getType(), stateItem.getValue(), stateItem.getUpdated()))
                    .collect(Collectors.toSet());
            result.getState().clear();
            result.getState().addAll(state);
        }

        result = this.createOrUpdate(result);
        if (!CollectionUtils.isEmpty(tags)) {
            updateTags(result, tags);
        }

        return Convert.my(result).scope(Scope.ALL).boom();
    }

    @CacheEvict(value = "drones", key = "#p0.externalId", beforeInvocation = true)
    public DroneDto update(DroneDto droneDto, List<TagDto> tags) {
        DroneJpa result = Convert.my(droneDto)
                                 .withJpa(findByGuid(droneDto.getId()))
                                 .scope(Scope.ALL)
                                 .boom();

        if (!CollectionUtils.isEmpty(droneDto.getState())) {
            Set<DeviceStateItemJpa> state = droneDto.getState()
                    .stream()
                    .map(stateItem -> new DeviceStateItemJpa(stateItem.getType(), stateItem.getValue(), stateItem.getUpdated()))
                    .collect(Collectors.toSet());
            result.getState().clear();
            result.getState().addAll(state);
        }

        result = (DroneJpa) update(result);
        if (!CollectionUtils.isEmpty(tags)) {
            updateTags(result, tags);
        }

        return Convert.my(result).scope(Scope.ALL).boom();
    }

    public IDrone update(IDrone drone) {
        DroneJpa result = (DroneJpa) drone;
        result = this.createOrUpdate(result);

        return result;
    }

    @Override
    @CacheEvict(value = "drones", allEntries = true, beforeInvocation = true)
    public void delete(String guid) {
        DroneJpa drone = (DroneJpa) findByGuid(guid);
        getDroneTagJpaRepository().deleteByEntity(drone);
        super.delete(guid);
    }

    public IDrone findByGuid(String guid) {
        return getRepository().findByGuid(guid);
    }

    @Cacheable(cacheNames = "drones", key = "#p0")
    public IDrone findByExternalId(String externalId, List<LazyLoadingField> fields) {
        IDrone result = getRepository().findByExternalId(externalId);
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

    public List<DroneDto> findAll(List<ProjectionDto> columns, PageRequest paging) {
        return getRepository().findAll(columns, paging)
                              .stream()
                              .map(drone -> (DroneDto) Convert.my(drone).boom())
                              .collect(Collectors.toList());
    }

    public PageResponseDto<DroneDto> findDrones(QueryFilterDto filter, Pageable paging) {
        Page<IDrone> page = getRepository().findEntities(filter, paging);

        return DroneSearchResultDto.builder()
                                   .numberOfItems((int) page.getTotalElements())
                                   .numberOfPages(page.getTotalPages())
                                   .data(page.getContent()
                                             .stream()
                                             .map(door -> (DroneDto) Convert.my(door).scope(Scope.ALL).boom())
                                             .collect(Collectors.toList())).build();
    }

    public Set<IDrone> findByRegions(Set<RegionJpa> regions, List<LazyLoadingField> fields) {
        Set<IDrone> result = getRepository().findByRegionsIn(regions);

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

    private void updateTags(DroneJpa drone, List<TagDto> tags) {
        if (tags != null && drone != null) {
            List<DroneTagJpa> existingTags = getDroneTagJpaRepository().findAllByEntityGuid(drone.getGuid());
            List<DroneTagJpa> toDelete = existingTags
                    .stream()
                    .filter(tag -> tags.stream().filter(t -> StringUtils.equals(t.getId(), tag.getGuid())).findFirst().orElse(null) == null)
                    .collect(Collectors.toList());
            toDelete.forEach(tag -> getDroneTagJpaRepository().delete(tag));

            List<TagDto> toAdd = tags
                    .stream()
                    .filter(tag -> existingTags.stream().filter(t -> StringUtils.equals(t.getGuid(), tag.getId())).findFirst().orElse(null) == null)
                    .collect(Collectors.toList());
            toAdd.forEach(tagDto -> {
                TagJpa tag = getTagJpaRepository().findByGuid(tagDto.getId());
                if (tag != null) {
                    DroneTagJpa droneTag = new DroneTagJpa();
                    droneTag.setGuid(UUID.randomUUID().toString());
                    droneTag.setEntity(drone);
                    droneTag.setTag(tag);
                    getDroneTagJpaRepository().save(droneTag);
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
