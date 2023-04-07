package com.iscweb.service.entity;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IApplicationEntity;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.AlertDto;
import com.iscweb.common.model.dto.entity.core.AlertSearchResultDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.dto.entity.core.DoorSearchResultDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.entity.IAlert;
import com.iscweb.common.model.entity.IDoor;
import com.iscweb.persistence.model.jpa.AlertJpa;
import com.iscweb.persistence.repositories.impl.AlertJpaRepository;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.iscweb.service.converter.Convert.GUID;

@Slf4j
@Service
public class AlertEntityService extends BaseJpaEntityService<AlertJpaRepository, AlertJpa> implements EntityService<AlertDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private AlertJpaRepository alertJpaRepository;

    @Override
    public AlertDto create(AlertDto dto) throws ServiceException {
        AlertJpa result = Convert.my(dto)
                .scope(Scope.ALL)
                .attr(GUID, true)
                .boom();

        result.setCreated(ZonedDateTime.now());
        result.setUpdated(result.getCreated());
        if (StringUtils.isEmpty(result.getGuid())) {
            result.setGuid(UUID.randomUUID().toString());
        }

        return Convert.my((IApplicationEntity) this.createOrUpdate(result)).scope(Scope.ALL).boom();
    }

    @Override
    public AlertDto update(AlertDto dto) throws ServiceException {
        AlertJpa result = Convert.my(dto)
                .withJpa(findByGuid(dto.getId()))
                .scope(Scope.ALL)
                .boom();

        result.setUpdated(ZonedDateTime.now());
        return Convert.my((IApplicationEntity) this.createOrUpdate(result)).scope(Scope.ALL).boom();
    }

    public AlertJpa findByGuid(String guid) {
        return getRepository().findByGuid(guid);
    }

    @Override
    public Logger getLogger() {
        return log;
    }

    public AlertDto findAlert(String triggerId, String deviceId, EntityType deviceType) {
        AlertJpa result = getRepository().findByTriggerIdAndDeviceIdAndDeviceType(triggerId, deviceId, deviceType);
        return result != null ? Convert.my(result).scope(Scope.ALL).boom() : null;
    }

    public PageResponseDto<AlertDto> findAlerts(QueryFilterDto filter, Pageable paging) {
        Page<IAlert> page = getRepository().findEntities(filter, paging);
        List<AlertDto> pageData = page.getContent()
                .stream()
                .map(alert -> (AlertDto) Convert.my(alert).scope(Scope.ALL).boom())
                .collect(Collectors.toList());
        return AlertSearchResultDto.builder()
                .numberOfItems((int) page.getTotalElements())
                .numberOfPages(page.getTotalPages())
                .data(pageData).build();
    }
}
