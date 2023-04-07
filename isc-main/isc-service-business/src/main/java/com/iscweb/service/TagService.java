package com.iscweb.service;

import com.iscweb.common.model.dto.ProjectionDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.TagDto;
import com.iscweb.common.model.dto.entity.core.TagSearchResultDto;
import com.iscweb.service.entity.TagEntityService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.Collections;
import java.util.List;

import static com.iscweb.common.security.ApplicationSecurity.*;

@Slf4j
@Service
public class TagService extends BaseBusinessService<TagDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private TagEntityService entityService;

    @PreAuthorize(IS_AUTHENTICATED)
    public TagSearchResultDto findTags(QueryFilterDto filter, PageRequest pageRequest) {
        return (TagSearchResultDto) getEntityService().findTags(filter, pageRequest);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public List<TagDto> findAll(List<ProjectionDto> columns, PageRequest pageRequest) {
        return getEntityService().findAll(columns, pageRequest);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public List<TagDto> findByDoorGuid(String doorId) {
        List<TagDto> result;
        try {
            result = getEntityService().findByDoorGuid(doorId);
        } catch (PersistenceException | DataAccessException e) {
            log.warn("Unable to load door tags for door id: {}", doorId);
            result = Collections.emptyList();
        }

        return result;
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public List<TagDto> findByUtilityGuid(String utilityId) {
        List<TagDto> result;
        try {
            result = getEntityService().findByUtilityGuid(utilityId);
        } catch (PersistenceException | DataAccessException e) {
            log.warn("Unable to load utilities tags for utility id: {}", utilityId);
            result = Collections.emptyList();
        }

        return result;
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public List<TagDto> findByCameraGuid(String cameraId) {
        List<TagDto> result;
        try {
            result = getEntityService().findByCameraGuid(cameraId);
        } catch (PersistenceException | DataAccessException e) {
            log.warn("Unable to load camera tags for camera id: {}", cameraId);
            result = Collections.emptyList();
        }

        return result;
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public List<TagDto> findByDroneGuid(String droneId) {
        List<TagDto> result;
        try {
            result = getEntityService().findByDroneGuid(droneId);
        } catch (PersistenceException | DataAccessException e) {
            log.warn("Unable to load drone tags for drone id: {}", droneId);
            result = Collections.emptyList();
        }

        return result;
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public List<TagDto> findBySpeakerGuid(String speakerId) {
        List<TagDto> result;
        try {
            result = getEntityService().findBySpeakerGuid(speakerId);
        } catch (PersistenceException | DataAccessException e) {
            log.warn("Unable to load speaker tags for speaker id: {}", speakerId);
            result = Collections.emptyList();
        }

        return result;
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public List<TagDto> findBySafetyGuid(String safetyId) {
        List<TagDto> result;
        try {
            result = getEntityService().findBySafetyGuid(safetyId);
        } catch (PersistenceException | DataAccessException e) {
            log.warn("Unable to load safety tags for safety id: {}", safetyId);
            result = Collections.emptyList();
        }

        return result;
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public List<TagDto> findByRadioGuid(String cameraId) {
        List<TagDto> result;
        try {
            result = getEntityService().findByRadioGuid(cameraId);
        } catch (PersistenceException | DataAccessException e) {
            log.warn("Unable to load camera tags for camera id: {}", cameraId);
            result = Collections.emptyList();
        }

        return result;
    }
}
