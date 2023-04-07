package com.iscweb.service.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.Lists;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.IApplicationEntity;
import com.iscweb.common.model.alert.AlertTriggerDto;
import com.iscweb.common.model.alert.AlertTriggerMatcherDto;
import com.iscweb.common.events.alerts.CustomAlertTriggerMatcher;
import com.iscweb.common.events.alerts.DateTimeAlertTriggerMatcher;
import com.iscweb.common.model.alert.AlertTriggerMatcherType;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.AlertTriggerSearchResultDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.model.entity.IAlertTrigger;
import com.iscweb.persistence.model.jpa.AlertTriggerJpa;
import com.iscweb.persistence.repositories.impl.AlertTriggerJpaRepository;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.iscweb.service.converter.Convert.GUID;

@Slf4j
@Service
public class AlertTriggerEntityService extends BaseJpaEntityService<AlertTriggerJpaRepository, AlertTriggerJpa> implements EntityService<AlertTriggerDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ObjectMapper jsonMapper;

    private final AtomicReference<List<AlertTriggerDto>> ACTIVE_CONFIGS = new AtomicReference<>(Lists.newArrayList());

    @Override
    public AlertTriggerDto create(AlertTriggerDto dto) throws ServiceException {
        AlertTriggerJpa result = Convert.my(dto)
                .scope(Scope.ALL)
                .attr(GUID, true)
                .boom();

        if (StringUtils.isEmpty(result.getGuid())) {
            result.setGuid(UUID.randomUUID().toString());
        }
        if (result.getCreated() == null) {
            result.setCreated(ZonedDateTime.now());
        }
        result.getMatchers().forEach(matcher -> {
            if (matcher.getType() == null) {
                matcher.setType(AlertTriggerMatcherType.CUSTOM);
            }
        });
        result.setUpdated(ZonedDateTime.now());

        AlertTriggerDto alertDto = Convert.my((IApplicationEntity) this.createOrUpdate(result)).scope(Scope.ALL).boom();
        refreshActiveList();
        return alertDto;
    }

    @Override
    @CacheEvict(value = "alertConfigs", key = "#p0.id", beforeInvocation = true)
    public AlertTriggerDto update(AlertTriggerDto dto) throws ServiceException {
        AlertTriggerJpa result = Convert.my(dto)
                .withJpa(findByGuid(dto.getId()))
                .scope(Scope.ALL)
                .boom();

        result.setUpdated(ZonedDateTime.now());
        result.getMatchers().forEach(matcher -> {
            if (matcher.getType() == null) {
                matcher.setType(AlertTriggerMatcherType.CUSTOM);
            }
        });
        AlertTriggerDto alertDto = Convert.my((IApplicationEntity) this.createOrUpdate(result)).scope(Scope.ALL).boom();
        refreshActiveList();
        return alertDto;
    }

    @CacheEvict(value = "alertConfigs", key = "#p0", beforeInvocation = true)
    public void delete(String guid) {
        super.delete(guid);
        refreshActiveList();
    }

    public AlertTriggerJpa findByGuid(String guid) {
        return getRepository().findByGuid(guid);
    }

    public List<AlertTriggerDto> activeConfigs() {
        return ACTIVE_CONFIGS.get();
    }

    public void refreshActiveList() {
        List<AlertTriggerDto> activeConfigs = Lists.newArrayList();

        List<AlertTriggerJpa> allConfigs = getRepository().findAll();
        for (AlertTriggerJpa config : allConfigs) {
            if (config.isActive()) {
                List<AlertTriggerMatcherDto> matchers = config.getMatchers()
                        .stream()
                        .map(matcher -> {
                            AlertTriggerMatcherDto matcherDto;
                            try {
                                switch (matcher.getType()) {
                                    case DATE_TIME:
                                        matcherDto = new DateTimeAlertTriggerMatcher(matcher.getBody(), getJsonMapper()); break;
                                    case CUSTOM:
                                        matcherDto = new CustomAlertTriggerMatcher(matcher.getBody(), getJsonMapper()); break;
                                    default:
                                        log.error("Unsupported alert matcher type: " + matcher.getType());
                                        matcherDto = null;
                                }
                            } catch (JsonProcessingException e) {
                                log.error("Unable to parse matcher. Config: '{}', matcher:\n{}", config.getName(), matcher.getBody());
                                matcherDto = null;
                            }

                            if (matcherDto != null) {
                                matcherDto.setType(matcher.getType());
                                matcherDto.setCreated(matcher.getCreated());
                                matcherDto.setUpdated(matcher.getUpdated());
                            }

                            return matcherDto;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                AlertTriggerDto alertConfig = Convert.my(config).scope(Scope.ALL).boom();
                alertConfig.setMatchers(matchers);
                activeConfigs.add(alertConfig);
            }
        }

        ACTIVE_CONFIGS.set(activeConfigs);
    }

    public AlertTriggerJpa findByName(String configName) {
        return getRepository().findByName(configName);
    }

    public PageResponseDto<AlertTriggerDto> findAlertTriggers(QueryFilterDto filter, Pageable paging) {
        Page<IAlertTrigger> page = getRepository().findEntities(filter, paging);
        List<AlertTriggerDto> pageData = page.getContent()
                .stream()
                .map(alertTrigger -> (AlertTriggerDto) Convert.my(alertTrigger).scope(Scope.ALL).boom())
                .collect(Collectors.toList());
        return AlertTriggerSearchResultDto.builder()
                .numberOfItems((int) page.getTotalElements())
                .numberOfPages(page.getTotalPages())
                .data(pageData).build();
    }

    @Override
    public Logger getLogger() {
        return log;
    }

}
