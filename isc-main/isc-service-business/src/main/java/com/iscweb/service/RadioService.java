package com.iscweb.service;

import com.google.common.collect.Sets;
import com.iscweb.common.annotations.CompositeCacheEntry;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.dto.entity.core.RadioDto;
import com.iscweb.common.model.dto.entity.core.RadioSearchResultDto;
import com.iscweb.persistence.model.jpa.RadioJpa;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.RadioEntityService;
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

/**
 * Radio devices business operations service.
 */
@Slf4j
@Service
public class RadioService extends BaseDeviceBusinessService<RadioDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RadioEntityService entityService;

    @PreAuthorize(IS_AUTHENTICATED)
    public DoorDto findByGuid(String guid, List<LazyLoadingField> fields) {
        final RadioJpa entity = getEntityService().findByGuid(guid);
        return entity != null ? Convert.my(entity).scope(Scope.fromLazyField(fields)).boom() : null;
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public List<RadioDto> findAll(PageRequest paging) {
        return getEntityService().findAll(paging)
                                 .stream()
                                 .map(jpa -> (RadioDto) Convert.my(jpa)
                                                              .scope(Scope.ALL)
                                                              .boom())
                                 .collect(Collectors.toList());
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public RadioSearchResultDto findRadios(QueryFilterDto filter, PageRequest pageRequest) {
        return (RadioSearchResultDto) getEntityService().findRadios(filter, pageRequest);
    }

    /**
     * Used for updating a single radio dto parents based on radio naming pattern.
     * @param radio object to update.
     */
    @CompositeCacheEntry(CompositeCacheEntry.UpdateType.UPDATE)
    public RadioDto updateRadioParents(RadioDto radio) {
        RadioDto result = radio;

        try {
            radio.setParentIds(Sets.newHashSet(resolveParentRegion(EntityType.RADIO, radio.getName())));
            result = getEntityService().update(radio, null);
        } catch (Exception e) {
            log.error(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
        }

        return result;
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public RadioJpa findByExternalId(String externalId, List<LazyLoadingField> deviceState) {
        return getEntityService().findByExternalId(externalId, deviceState);
    }
}
