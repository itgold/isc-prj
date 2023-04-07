package com.iscweb.service;

import com.iscweb.common.model.alert.AlertTriggerDto;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.response.PageResponseDto;
import com.iscweb.common.security.ApplicationSecurity;
import com.iscweb.common.security.ISystemUser;
import com.iscweb.persistence.model.jpa.AlertTriggerJpa;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.AlertTriggerEntityService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

@Slf4j
@Service
public class AlertTriggerService extends BaseBusinessService<AlertTriggerDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private AlertTriggerEntityService entityService;

    @PostConstruct
    public void initialize() {
        try (ISystemUser ignored = ApplicationSecurity.systemUserLogin()) {
            getEntityService().refreshActiveList();
        }
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public List<AlertTriggerDto> findAll(PageRequest paging) {
        return getEntityService().findAll(paging)
                                 .stream()
                                 .map(jpa -> (AlertTriggerDto) Convert.my(jpa)
                                                              .scope(Scope.ALL)
                                                              .boom())
                                 .collect(Collectors.toList());
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public PageResponseDto<AlertTriggerDto> findAlertTriggers(QueryFilterDto filter, PageRequest pageRequest) {
        return getEntityService().findAlertTriggers(filter, pageRequest);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public AlertTriggerDto findByName(String configName) {
        AlertTriggerJpa config = getEntityService().findByName(configName);
        return config != null ? Convert.my(config).scope(Scope.ALL).boom() : null;
    }
}
