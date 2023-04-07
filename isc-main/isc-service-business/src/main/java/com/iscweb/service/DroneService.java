package com.iscweb.service;

import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.dto.entity.core.DroneDto;
import com.iscweb.common.model.dto.entity.core.DroneSearchResultDto;
import com.iscweb.common.model.entity.IDrone;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.DroneEntityService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

@Service
public class DroneService extends BaseDeviceBusinessService<DroneDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DroneEntityService entityService;

    @PreAuthorize(IS_AUTHENTICATED)
    public List<DroneDto> findAll(PageRequest pageRequest) {
        return getEntityService().findAll(pageRequest)
                .stream()
                .map(jpa -> (DroneDto) Convert.my(jpa)
                        .scope(Scope.ALL)
                        .boom())
                .collect(Collectors.toList());
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public DroneSearchResultDto findDrones(QueryFilterDto filter, PageRequest pageRequest) {
        return (DroneSearchResultDto) getEntityService().findDrones(filter, pageRequest);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public IDrone findByExternalId(String externalId, List<LazyLoadingField> deviceState) {
        return getEntityService().findByExternalId(externalId, deviceState);
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public DoorDto findByGuid(String guid, List<LazyLoadingField> fields) {
        final IDrone entity = getEntityService().findByGuid(guid);
        return entity != null ? Convert.my(entity).scope(Scope.fromLazyField(fields)).boom() : null;
    }
}
