package com.iscweb.service.entity;

import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.SpeakerDto;
import com.iscweb.common.model.dto.entity.core.SpeakerSearchResultDto;
import com.iscweb.common.model.dto.entity.core.TagDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.entity.ISpeaker;
import com.iscweb.persistence.model.jpa.DeviceStateItemJpa;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.model.jpa.SpeakerJpa;
import com.iscweb.persistence.model.jpa.SpeakerTagJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.repositories.impl.SpeakerJpaRepository;
import com.iscweb.persistence.repositories.impl.SpeakerTagJpaRepository;
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
 * Service class for speakers.
 *
 * @author alex@iscweb.io
 */
@Slf4j
@Service
public class SpeakerEntityService extends BaseRegionEntityService<SpeakerJpaRepository, ISpeaker> implements IDeviceEntityService<SpeakerDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagJpaRepository tagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SpeakerTagJpaRepository speakerTagJpaRepository;

    public SpeakerDto create(SpeakerDto speakerDto, List<TagDto> tags) {
        SpeakerJpa result = Convert.my(speakerDto)
                                   .scope(Scope.ALL)
                                   .attr(GUID, true)
                                   .boom();

        if (!CollectionUtils.isEmpty(speakerDto.getState())) {
            Set<DeviceStateItemJpa> state = speakerDto.getState()
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

    @CacheEvict(value = "speakers", key = "#p0.externalId", beforeInvocation = true)
    public SpeakerDto update(SpeakerDto speakerDto, List<TagDto> tags) {
        SpeakerJpa result = Convert.my(speakerDto)
                                   .withJpa(findByGuid(speakerDto.getId()))
                                   .scope(Scope.ALL).boom();

        if (!CollectionUtils.isEmpty(speakerDto.getState())) {
            Set<DeviceStateItemJpa> state = speakerDto.getState()
                    .stream()
                    .map(stateItem -> new DeviceStateItemJpa(stateItem.getType(), stateItem.getValue(), stateItem.getUpdated()))
                    .collect(Collectors.toSet());
            result.getState().clear();
            result.getState().addAll(state);
        }

        result = (SpeakerJpa) update(result);
        if (!CollectionUtils.isEmpty(tags)) {
            updateTags(result, tags);
        }

        return Convert.my(result).scope(Scope.ALL).boom();
    }

    @Override
    @CacheEvict(value = "speakers", allEntries = true, beforeInvocation = true)
    public void delete(String guid) {
        SpeakerJpa speaker = (SpeakerJpa) findByGuid(guid);
        getSpeakerTagJpaRepository().deleteByEntity(speaker);
        super.delete(guid);
    }

    public ISpeaker findByGuid(String guid) {
        return getRepository().findByGuid(guid);
    }

    @Cacheable(cacheNames = "speakers", key = "#p0")
    public ISpeaker findByExternalId(String externalId, List<LazyLoadingField> fields) {
        ISpeaker result = getRepository().findByExternalId(externalId);
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

    public List<SpeakerDto> findAll(List<ProjectionDto> columns, PageRequest paging) {
        return getRepository().findAll(columns, paging)
                              .stream()
                              .map(speaker -> (SpeakerDto) Convert.my(speaker).boom())
                              .collect(Collectors.toList());
    }

    public PageResponseDto<SpeakerDto> findSpeakers(QueryFilterDto filter, Pageable paging) {
        Page<ISpeaker> page = getRepository().findEntities(filter, paging);

        return SpeakerSearchResultDto.builder()
                                     .numberOfItems((int) page.getTotalElements())
                                     .numberOfPages(page.getTotalPages())
                                     .data(page.getContent()
                                               .stream()
                                               .map(door -> (SpeakerDto) Convert.my(door).scope(Scope.ALL).boom())
                                               .collect(Collectors.toList())).build();
    }

    public Set<ISpeaker> findByRegions(Set<RegionJpa> regions, List<LazyLoadingField> fields) {
        Set<ISpeaker> result = getRepository().findByRegionsIn(regions);

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

    private ISpeaker update(ISpeaker speaker) {
        SpeakerJpa result = (SpeakerJpa) speaker;
        result = this.createOrUpdate(result);

        return result;
    }

    private void updateTags(SpeakerJpa speaker, List<TagDto> tags) {
        if (tags != null && speaker != null) {
            List<SpeakerTagJpa> existingTags = getSpeakerTagJpaRepository().findAllByEntityGuid(speaker.getGuid());
            List<SpeakerTagJpa> toDelete = existingTags
                    .stream()
                    .filter(tag -> tags.stream().filter(t -> StringUtils.equals(t.getId(), tag.getGuid())).findFirst().orElse(null) == null)
                    .collect(Collectors.toList());
            toDelete.forEach(tag -> getSpeakerTagJpaRepository().delete(tag));

            List<TagDto> toAdd = tags
                    .stream()
                    .filter(tag -> existingTags.stream().filter(t -> StringUtils.equals(t.getGuid(), tag.getId())).findFirst().orElse(null) == null)
                    .collect(Collectors.toList());
            toAdd.forEach(tagDto -> {
                TagJpa tag = getTagJpaRepository().findByGuid(tagDto.getId());
                if (tag != null) {
                    SpeakerTagJpa speakerTag = new SpeakerTagJpa();
                    speakerTag.setGuid(UUID.randomUUID().toString());
                    speakerTag.setEntity(speaker);
                    speakerTag.setTag(tag);
                    getSpeakerTagJpaRepository().save(speakerTag);
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
