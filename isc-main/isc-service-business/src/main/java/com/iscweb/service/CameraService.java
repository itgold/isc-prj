package com.iscweb.service;

import com.google.common.collect.Sets;
import com.iscweb.common.annotations.CompositeCacheEntry;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.model.dto.entity.core.CameraSearchResultDto;
import com.iscweb.common.model.dto.entity.core.RegionDto;
import com.iscweb.common.model.entity.ICamera;
import com.iscweb.common.model.metadata.RegionType;
import com.iscweb.common.service.integration.camera.ICameraService;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.CameraEntityService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

@Slf4j
@Service
public class CameraService extends BaseDeviceBusinessService<CameraDto> {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CameraEntityService entityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ICameraService cameraDetailsService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RegionService regionService;

    @PreAuthorize(IS_AUTHENTICATED)
    public CameraDto findByGuid(String guid, List<LazyLoadingField> fields) {
        ICamera entity = getEntityService().findByGuid(guid, fields);
        return entity != null ? Convert.my(entity).scope(Scope.fromLazyField(fields)).boom() : null;
    }

    private static final Pattern[] GROUP_NAMING_PATTERS = { Pattern.compile("^(.*)Cam[- ]*\\d") };

    @PreAuthorize(IS_AUTHENTICATED)
    public List<CameraDto> findAll(PageRequest pageRequest) {
        return getEntityService()
                .findAll(pageRequest)
                .stream()
                .map(jpa -> (CameraDto) Convert.my(jpa)
                        .scope(Scope.ALL)
                        .boom())
                .map(camera -> getCameraDetailsService().getCameraDetails(camera))
                .collect(Collectors.toList());
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public CameraSearchResultDto findCameras(QueryFilterDto filter, PageRequest pageRequest) {
        return (CameraSearchResultDto) getEntityService().findCameras(filter, pageRequest);
    }

    /**
     * Find if the camera name matching to know naming convention and group cameras under one parent region with type RegionType::POINT_REGION
     * to be represented as single item on the dashboard map.
     *
     * @param camera object to update.
     */
    @CompositeCacheEntry(CompositeCacheEntry.UpdateType.UPDATE)
    public CameraDto updateCameraGroups(CameraDto camera) throws ServiceException {
        CameraDto result = camera;
        try {
            String baseName = null;
            for (Pattern pattern : GROUP_NAMING_PATTERS) {
                Matcher m = pattern.matcher(camera.getName());
                if (m.matches() && m.groupCount() == 1) {
                    baseName = m.group(1).trim();
                    break;
                }
            }

            if (!StringUtils.isEmpty(baseName)) {
                camera.setParentIds(Sets.newHashSet(resolveParentRegion(baseName, camera)));
                result = getEntityService().update(camera, null);
                log.info("Parent camera group region updated for the camera: " + camera.getName());
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Unable to update parent region", e);
        }

        return result;
    }

    private String resolveParentRegion(String baseName, CameraDto camera) throws ServiceException {
        RegionDto parentRegion = getRegionService().findByName(baseName);
        if (parentRegion == null) {
            parentRegion = new RegionDto();
            parentRegion.setName(baseName);
            parentRegion.setParentIds(camera.getParentIds());
            parentRegion.setType(RegionType.POINT_REGION);
            parentRegion.setSubType(EntityType.CAMERA.name());
            parentRegion = getRegionService().create(parentRegion);
        }

        return parentRegion.getId();
    }

    @PreAuthorize(IS_AUTHENTICATED)
    public ICamera findByExternalId(String externalId, List<LazyLoadingField> deviceState) {
        return getEntityService().findByExternalId(externalId, deviceState);
    }
}
