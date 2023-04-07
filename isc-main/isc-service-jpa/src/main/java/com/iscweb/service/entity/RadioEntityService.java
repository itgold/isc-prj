package com.iscweb.service.entity;

import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.RadioDto;
import com.iscweb.common.model.dto.entity.core.RadioSearchResultDto;
import com.iscweb.common.model.dto.entity.core.TagDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.persistence.model.jpa.DeviceStateItemJpa;
import com.iscweb.persistence.model.jpa.RadioJpa;
import com.iscweb.persistence.model.jpa.RadioTagJpa;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.repositories.impl.RadioJpaRepository;
import com.iscweb.persistence.repositories.impl.RadioTagJpaRepository;
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
 * Service class for radios.
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
public class RadioEntityService extends BaseRegionEntityService<RadioJpaRepository, RadioJpa> implements IDeviceEntityService<RadioDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RadioTagJpaRepository radioTagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagJpaRepository tagJpaRepository;

    public RadioDto create(RadioDto radioDto, List<TagDto> tags) {
        RadioJpa result = Convert.my(radioDto)
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

    @CacheEvict(value = "radios", key = "#p0.externalId", beforeInvocation = true)
    public RadioDto update(RadioDto radioDto, List<TagDto> tags) {
        RadioJpa result = Convert.my(radioDto)
                                .withJpa(findByGuid(radioDto.getId()))
                                .scope(Scope.ALL)
                                .boom();

        if (!CollectionUtils.isEmpty(radioDto.getState())) {
            Set<DeviceStateItemJpa> state = radioDto.getState()
                                                   .stream()
                                                   .map(stateItem -> new DeviceStateItemJpa(stateItem.getType(), stateItem.getValue(), stateItem.getUpdated()))
                                                   .collect(Collectors.toSet());
            result.getState().clear();
            result.getState().addAll(state);
        }

        result = (RadioJpa) update(result);
        if (!CollectionUtils.isEmpty(tags)) {
            updateTags(result, tags);
        }

        return Convert.my(result).scope(Scope.ALL).boom();
    }

    public RadioJpa update(RadioJpa radio) {
        RadioJpa result = this.createOrUpdate(radio);
        return result;
    }

    @Override
    @CacheEvict(value = "radios", allEntries = true, beforeInvocation = true)
    public void delete(String guid) {
        RadioJpa radioJpa = findByGuid(guid);
        this.getRadioTagJpaRepository().deleteByEntity(radioJpa);
        super.delete(guid);
    }

    public RadioJpa findByGuid(String guid) {
        return getRepository().findByGuid(guid);
    }

    @Cacheable(cacheNames = "radios", key = "#p0")
    public RadioJpa findByExternalId(String externalId, List<LazyLoadingField> fields) {
        RadioJpa result = getRepository().findByExternalId(externalId);
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

    public List<RadioDto> findAll(List<ProjectionDto> columns, PageRequest paging) {
        return getRepository().findAll(columns, paging)
                              .stream()
                              .map(radioJpa -> (RadioDto) Convert.my(radioJpa).boom())
                              .collect(Collectors.toList());
    }

    public RadioDto getRadioDtoByGuid(String guid) {
        RadioDto out = null;
        RadioJpa radioJpa = getRepository().findByGuid(guid);
        if (null != radioJpa) {
            out = Convert.my(radioJpa).scope(Scope.ALL).boom();
        }
        return out;
    }

    public Set<RadioJpa> findByRegions(Set<RegionJpa> regions, List<LazyLoadingField> fields) {
        Set<RadioJpa> result = getRepository().findByRegionsIn(regions);

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

    public PageResponseDto<RadioDto> findRadios(QueryFilterDto filter, Pageable paging) {
        Page<RadioJpa> page = getRepository().findEntities(filter, paging);
        List<RadioDto> pageData = page.getContent()
                                     .stream()
                                     .map(radioJpa -> (RadioDto) Convert.my(radioJpa).scope(Scope.ALL).boom())
                                     .collect(Collectors.toList());
        return RadioSearchResultDto.builder()
                                  .numberOfItems((int) page.getTotalElements())
                                  .numberOfPages(page.getTotalPages())
                                  .data(pageData).build();
    }

    private void updateTags(RadioJpa radioJpa, List<TagDto> tags) {
        if (tags != null && radioJpa != null) {
            List<RadioTagJpa> existingTags = this.getRadioTagJpaRepository().findAllByEntityGuid(radioJpa.getGuid());
            List<RadioTagJpa> toDelete = existingTags
                    .stream()
                    .filter(tag -> tags.stream().filter(t -> StringUtils.equals(t.getId(), tag.getGuid())).findFirst().orElse(null) == null)
                    .collect(Collectors.toList());
            toDelete.forEach(tag -> this.getRadioTagJpaRepository().delete(tag));

            List<TagDto> toAdd = tags
                    .stream()
                    .filter(tag -> existingTags.stream().filter(t -> StringUtils.equals(t.getGuid(), tag.getId())).findFirst().orElse(null) == null)
                    .collect(Collectors.toList());
            toAdd.forEach(tagDto -> {
                TagJpa tag = getTagJpaRepository().findByGuid(tagDto.getId());
                if (tag != null) {
                    RadioTagJpa radioTagJpa = new RadioTagJpa();
                    radioTagJpa.setGuid(UUID.randomUUID().toString());
                    radioTagJpa.setEntity(radioJpa);
                    radioTagJpa.setTag(tag);
                    this.getRadioTagJpaRepository().save(radioTagJpa);
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
