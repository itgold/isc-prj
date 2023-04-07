package com.iscweb.service;

import com.google.common.collect.Sets;
import com.iscweb.common.annotations.CompositeCacheEntry;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.SpeakerDto;
import com.iscweb.common.model.dto.entity.core.SpeakerSearchResultDto;
import com.iscweb.common.model.entity.ISpeaker;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.SpeakerEntityService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

@Slf4j
@Service
public class SpeakerService extends BaseDeviceBusinessService<SpeakerDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SpeakerEntityService entityService;

    @PreAuthorize(IS_AUTHENTICATED)
    public SpeakerDto findByGuid(String guid) {
        final ISpeaker entity = getEntityService().findByGuid(guid);
        return entity != null ? Convert.my(entity).scope(Scope.ALL).boom() : null;
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public List<SpeakerDto> findAll(PageRequest pageRequest) {
        return getEntityService().findAll(pageRequest)
                                 .stream()
                                 .map(jpa -> (SpeakerDto) Convert.my(jpa)
                                                                 .scope(Scope.ALL)
                                                                 .boom())
                                 .collect(Collectors.toList());
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public SpeakerSearchResultDto findSpeakers(QueryFilterDto filter, PageRequest pageRequest) {
        return (SpeakerSearchResultDto) getEntityService().findSpeakers(filter, pageRequest);
    }

    /**
     * Used for updating a single speaker dto parents based on speaker naming pattern.
     * @param speaker object to update.
     */
    @CompositeCacheEntry(CompositeCacheEntry.UpdateType.UPDATE)
    public SpeakerDto updateSpeakerParents(SpeakerDto speaker) {
        SpeakerDto result = speaker;
        try {
            log.info("resolving parent for speaker: " + speaker.getName());
            speaker.setParentIds(Sets.newHashSet(resolveParentRegion(EntityType.SPEAKER, speaker.getName())));
            result = getEntityService().update(speaker, null);
        } catch (Exception e) {
            log.error(e.getCause() != null ? e.getCause().toString() : e.toString());
        }

        return result;
    }
}
