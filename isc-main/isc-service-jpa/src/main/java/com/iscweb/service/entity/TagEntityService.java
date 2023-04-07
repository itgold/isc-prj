package com.iscweb.service.entity;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.TagDto;
import com.iscweb.common.model.dto.entity.core.TagSearchResultDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.entity.ITag;
import com.iscweb.persistence.model.jpa.CameraTagJpa;
import com.iscweb.persistence.model.jpa.DoorTagJpa;
import com.iscweb.persistence.model.jpa.DroneTagJpa;
import com.iscweb.persistence.model.jpa.RadioTagJpa;
import com.iscweb.persistence.model.jpa.SafetyTagJpa;
import com.iscweb.persistence.model.jpa.SpeakerTagJpa;
import com.iscweb.persistence.model.jpa.TagJpa;
import com.iscweb.persistence.model.jpa.UtilityTagJpa;
import com.iscweb.persistence.repositories.impl.AllTagsJpaRepository;
import com.iscweb.persistence.repositories.impl.CameraTagJpaRepository;
import com.iscweb.persistence.repositories.impl.DoorTagJpaRepository;
import com.iscweb.persistence.repositories.impl.DroneTagJpaRepository;
import com.iscweb.persistence.repositories.impl.RadioTagJpaRepository;
import com.iscweb.persistence.repositories.impl.SafetyTagJpaRepository;
import com.iscweb.persistence.repositories.impl.SpeakerTagJpaRepository;
import com.iscweb.persistence.repositories.impl.TagJpaRepository;
import com.iscweb.persistence.repositories.impl.UtilityTagJpaRepository;
import com.iscweb.service.converter.Convert;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for tags.
 *
 * @author dmorozov
 */
@Slf4j
@Service
public class TagEntityService extends BaseJpaEntityService<TagJpaRepository, ITag> implements EntityService<TagDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DoorTagJpaRepository doorTagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CameraTagJpaRepository cameraTagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DroneTagJpaRepository droneTagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SpeakerTagJpaRepository speakerTagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private AllTagsJpaRepository allTagsJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private UtilityTagJpaRepository utilityTagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SafetyTagJpaRepository safetyTagJpaRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RadioTagJpaRepository radioTagJpaRepository;

    public TagDto create(TagDto tagDto) throws ServiceException {
        TagJpa result = Convert.my(tagDto)
                               .boom();
        result.setGuid(tagDto.getName());

        result = getRepository().save(result);

        return Convert.my(result).boom();
    }

    public TagDto update(TagDto tagDto) throws ServiceException {
        String guid = tagDto.getId();
        TagJpa tagJpa = getRepository().findByGuid(guid);

        if (tagJpa == null) {
            throw new ServiceException(String.format("Tag %s cannot be found.", guid));
        }

        tagJpa.setName(tagDto.getName());
        tagJpa = getRepository().save(tagJpa);

        return Convert.my(tagJpa).boom();
    }

    @Override
    public void delete(String guid) {
        delete(getRepository().findByGuid(guid));
    }

    @Override
    public void delete(Long entityId) {
        delete(getRepository().findById(entityId).orElse(null));
    }

    public PageResponseDto<TagDto> findTags(QueryFilterDto filter, Pageable paging) {
        Page<ITag> page = getRepository().findEntities(filter, paging);

        return TagSearchResultDto.builder()
                                 .numberOfItems((int) page.getTotalElements())
                                 .numberOfPages(page.getTotalPages())
                                 .data(page.getContent()
                                           .stream()
                                           .map(door -> (TagDto) Convert.my(door).boom())
                                           .collect(Collectors.toList())).build();
    }

    public List<TagDto> findAll(List<ProjectionDto> columns, PageRequest paging) {
        List<ITag> tags = getRepository().findAll(columns, paging);
        return tags
                .stream()
                .map(tag -> (TagDto) Convert.my(tag).boom())
                .collect(Collectors.toList());
    }

    public List<TagDto> findByDoorGuid(String doorGuid) {
        List<DoorTagJpa> tags = getDoorTagJpaRepository().findAllByEntityGuid(doorGuid);
        return tags
                .stream()
                .map(tag -> (TagDto) Convert.my(tag.getTag()).boom())
                .collect(Collectors.toList());
    }

    public List<TagDto> findByCameraGuid(String cameraGuid) {
        List<CameraTagJpa> tags = getCameraTagJpaRepository().findAllByEntityGuid(cameraGuid);
        return tags
                .stream()
                .map(tag -> (TagDto) Convert.my(tag.getTag()).boom())
                .collect(Collectors.toList());
    }

    public List<TagDto> findByDroneGuid(String droneGuid) {
        List<DroneTagJpa> tags = getDroneTagJpaRepository().findAllByEntityGuid(droneGuid);
        return tags
                .stream()
                .map(tag -> (TagDto) Convert.my(tag.getTag()).boom())
                .collect(Collectors.toList());
    }

    public List<TagDto> findBySpeakerGuid(String speakerGuid) {
        List<SpeakerTagJpa> tags = getSpeakerTagJpaRepository().findAllByEntityGuid(speakerGuid);
        return tags
                .stream()
                .map(tag -> (TagDto) Convert.my(tag.getTag()).boom())
                .collect(Collectors.toList());
    }

    public List<TagDto> findByUtilityGuid(String utilityGuid) {
        List<UtilityTagJpa> tags = getUtilityTagJpaRepository().findAllByEntityGuid(utilityGuid);
        return tags
                .stream()
                .map(tag -> (TagDto) Convert.my(tag.getTag()).boom())
                .collect(Collectors.toList());
    }

    public List<TagDto> findBySafetyGuid(String utilityGuid) {
        List<SafetyTagJpa> tags = getSafetyTagJpaRepository().findAllByEntityGuid(utilityGuid);
        return tags
                .stream()
                .map(tag -> (TagDto) Convert.my(tag.getTag()).boom())
                .collect(Collectors.toList());
    }

    public List<TagDto> findByRadioGuid(String utilityGuid) {
        List<RadioTagJpa> tags = getRadioTagJpaRepository().findAllByEntityGuid(utilityGuid);
        return tags
                .stream()
                .map(tag -> (TagDto) Convert.my(tag.getTag()).boom())
                .collect(Collectors.toList());
    }

    private void delete(TagJpa tag) {
        if (tag != null) {
            getAllTagsJpaRepository().deleteByTagId(tag.getId());
            getRepository().delete(tag);
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
