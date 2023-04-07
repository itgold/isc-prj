package com.iscweb.service.entity;

import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.model.dto.entity.core.CameraSearchResultDto;
import com.iscweb.common.model.dto.entity.core.TagDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.entity.ICamera;
import com.iscweb.persistence.model.jpa.CameraJpa;
import com.iscweb.persistence.model.jpa.CameraTagJpa;
import com.iscweb.persistence.model.jpa.DeviceStateItemJpa;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.repositories.impl.CameraJpaRepository;
import com.iscweb.persistence.repositories.impl.CameraTagJpaRepository;
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
 * Service class for cameras.
 *
 * @author skurenkov
 * @author dmorozov
 */
@Slf4j
@Service
public class CameraEntityService extends BaseRegionEntityService<CameraJpaRepository, ICamera> implements IDeviceEntityService<CameraDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagJpaRepository tagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CameraTagJpaRepository cameraTagJpaRepository;

    public CameraDto create(CameraDto cameraDto, List<TagDto> tags) {

        CameraJpa result = Convert.my(cameraDto)
                                  .scope(Scope.ALL)
                                  .attr(GUID, true)
                                  .boom();

        if (!CollectionUtils.isEmpty(cameraDto.getState())) {
            Set<DeviceStateItemJpa> state = cameraDto.getState()
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

    @CacheEvict(value = "cameras", key = "#p0.externalId", beforeInvocation = true)
    public CameraDto update(CameraDto cameraDto, List<TagDto> tags) {
        CameraJpa result = Convert.my(cameraDto)
                .withJpa(findByGuid(cameraDto.getId()))
                .scope(Scope.ALL).boom();

        if (!CollectionUtils.isEmpty(cameraDto.getState())) {
            Set<DeviceStateItemJpa> state = cameraDto.getState()
                    .stream()
                    .map(stateItem -> new DeviceStateItemJpa(stateItem.getType(), stateItem.getValue(), stateItem.getUpdated()))
                    .collect(Collectors.toSet());
            result.getState().clear();
            result.getState().addAll(state);
        }

        result = (CameraJpa) update(result);
        if (!CollectionUtils.isEmpty(tags)) {
            updateTags(result, tags);
        }

        return Convert.my(result).scope(Scope.ALL).boom();
    }

    public ICamera update(ICamera camera) {
        CameraJpa result = (CameraJpa) camera;
        result = this.createOrUpdate(result);

        return result;
    }

    @Override
    @CacheEvict(value = "cameras", allEntries = true, beforeInvocation = true)
    public void delete(String guid) {
        CameraJpa camera = (CameraJpa) findByGuid(guid);
        getCameraTagJpaRepository().deleteByEntity(camera);
        super.delete(guid);
    }

    public ICamera findByGuid(String guid) {
        return getRepository().findByGuid(guid);
    }

    public ICamera findByGuid(String guid, List<LazyLoadingField> fields) {
        return initializeFields(getRepository().findByGuid(guid), fields);
    }

    @Cacheable(cacheNames = "cameras", key = "#p0")
    public ICamera findByExternalId(String externalId, List<LazyLoadingField> fields) {
        ICamera result = getRepository().findByExternalId(externalId);
        return initializeFields(result, fields);
    }

    private ICamera initializeFields(ICamera result, List<LazyLoadingField> fields) {
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

    public List<CameraDto> findAll(List<ProjectionDto> columns, PageRequest paging) {
        return getRepository().findAll(columns, paging)
                              .stream()
                              .map(camera -> (CameraDto) Convert.my(camera).boom())
                              .collect(Collectors.toList());
    }

    public PageResponseDto<CameraDto> findCameras(QueryFilterDto filter, Pageable paging) {
        Page<ICamera> page = getRepository().findEntities(filter, paging);

        return CameraSearchResultDto.builder()
                                    .numberOfItems((int) page.getTotalElements())
                                    .numberOfPages(page.getTotalPages())
                                    .data(page.getContent()
                                              .stream()
                                              .map(door -> (CameraDto) Convert.my(door).scope(Scope.ALL).boom())
                                              .collect(Collectors.toList())).build();
    }

    public Set<ICamera> findByRegions(Set<RegionJpa> regions, List<LazyLoadingField> fields) {
        Set<ICamera> result = getRepository().findByRegionsIn(regions);

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

    private void updateTags(CameraJpa camera, List<TagDto> tags) {
        if (tags != null && camera != null) {
            List<CameraTagJpa> existingTags = getCameraTagJpaRepository().findAllByEntityGuid(camera.getGuid());
            List<CameraTagJpa> toDelete = existingTags
                    .stream()
                    .filter(tag -> tags.stream().filter(t -> StringUtils.equals(t.getId(), tag.getGuid())).findFirst().orElse(null) == null)
                    .collect(Collectors.toList());
            toDelete.forEach(tag -> getCameraTagJpaRepository().delete(tag));

            List<TagDto> toAdd = tags
                    .stream()
                    .filter(tag -> existingTags.stream().filter(t -> StringUtils.equals(t.getGuid(), tag.getId())).findFirst().orElse(null) == null)
                    .collect(Collectors.toList());
            toAdd.forEach(tagDto -> {
                TagJpa tag = getTagJpaRepository().findByGuid(tagDto.getId());
                if (tag != null) {
                    CameraTagJpa cameraTag = new CameraTagJpa();
                    cameraTag.setGuid(UUID.randomUUID().toString());
                    cameraTag.setEntity(camera);
                    cameraTag.setTag(tag);
                    getCameraTagJpaRepository().save(cameraTag);
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
