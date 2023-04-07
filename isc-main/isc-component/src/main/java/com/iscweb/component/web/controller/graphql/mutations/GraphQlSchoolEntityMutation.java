package com.iscweb.component.web.controller.graphql.mutations;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.dto.entity.core.DroneDto;
import com.iscweb.common.model.dto.entity.core.RadioDto;
import com.iscweb.common.model.dto.entity.core.RegionDto;
import com.iscweb.common.model.dto.entity.core.SafetyDto;
import com.iscweb.common.model.dto.entity.core.SpeakerDto;
import com.iscweb.common.model.dto.entity.core.TagDto;
import com.iscweb.common.model.dto.entity.core.UtilityDto;
import com.iscweb.common.model.dto.entity.core.ZoneDto;
import com.iscweb.common.model.entity.IRegion;
import com.iscweb.common.model.metadata.RegionType;
import com.iscweb.component.web.controller.graphql.common.UpdateResultDto;
import com.iscweb.service.CameraService;
import com.iscweb.service.DoorService;
import com.iscweb.service.DroneService;
import com.iscweb.service.RadioService;
import com.iscweb.service.RegionService;
import com.iscweb.service.SafetyService;
import com.iscweb.service.SpeakerService;
import com.iscweb.service.UtilityService;
import com.iscweb.service.ZoneService;
import com.iscweb.service.composite.CompositeService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Generic GraphQL mutation for school entities.
 */
@Component
public class GraphQlSchoolEntityMutation implements GraphQLMutationResolver {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DoorService doorService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CameraService cameraService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DroneService droneService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RegionService regionService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CompositeService compositeService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ZoneService zoneService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SpeakerService speakerService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private UtilityService utilityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SafetyService safetyService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RadioService radioService;

    public SafetyDto newSafety(SafetyDto safety, List<TagDto> tags) throws ServiceException {
        return getSafetyService().create(safety, tags);
    }

    public SafetyDto updateSafety(SafetyDto safety, List<TagDto> tags) throws ServiceException {
        return getSafetyService().update(safety, tags);
    }

    public UpdateResultDto deleteSafety(String safetyId) {
        getSafetyService().delete(safetyId);
        return new UpdateResultDto(UpdateResultDto.Status.SUCCESS.name(), safetyId);
    }

    public DoorDto newDoor(DoorDto door, List<TagDto> tags) throws ServiceException {
        return getDoorService().create(door, tags);
    }

    public DoorDto updateDoor(DoorDto door, List<TagDto> tags) throws ServiceException {
        return getDoorService().update(door, tags);
    }

    public UpdateResultDto deleteDoor(String doorId) {
        getDoorService().delete(doorId);
        return new UpdateResultDto(UpdateResultDto.Status.SUCCESS.name(), doorId);
    }

    public UtilityDto newUtility(UtilityDto utility, List<TagDto> tags) throws ServiceException {
        return getUtilityService().create(utility, tags);
    }

    public UtilityDto updateUtility(UtilityDto utility, List<TagDto> tags) throws ServiceException {
        return getUtilityService().update(utility, tags);
    }

    public UpdateResultDto deleteUtility(String utilityId) {
        getUtilityService().delete(utilityId);
        return new UpdateResultDto(UpdateResultDto.Status.SUCCESS.name(), utilityId);
    }

    public SpeakerDto newSpeaker(SpeakerDto speaker, List<TagDto> tags) throws ServiceException {
        return getSpeakerService().create(speaker, tags);
    }

    public SpeakerDto updateSpeaker(SpeakerDto speaker, List<TagDto> tags) throws ServiceException {
        return getSpeakerService().update(speaker, tags);
    }

    public UpdateResultDto deleteSpeaker(String speakerId) {
        getSpeakerService().delete(speakerId);
        return new UpdateResultDto(UpdateResultDto.Status.SUCCESS.name(), speakerId);
    }

    public CameraDto newCamera(CameraDto cameraDto, List<TagDto> tags) throws ServiceException {
        return getCameraService().create(cameraDto, tags);
    }

    public CameraDto updateCamera(CameraDto cameraDto, List<TagDto> tags) throws ServiceException {
        return getCameraService().update(cameraDto, tags);
    }

    public UpdateResultDto deleteCamera(String cameraId) {
        getCameraService().delete(cameraId);
        return new UpdateResultDto(UpdateResultDto.Status.SUCCESS.name(), cameraId);
    }

    public DroneDto newDrone(DroneDto droneDto, List<TagDto> tags) throws ServiceException {
        return getDroneService().create(droneDto, tags);
    }

    public DroneDto updateDrone(DroneDto droneDto, List<TagDto> tags) throws ServiceException {
        return getDroneService().update(droneDto, tags);
    }

    public UpdateResultDto deleteDrone(String droneId) {
        getDroneService().delete(droneId);
        return new UpdateResultDto(UpdateResultDto.Status.SUCCESS.name(), droneId);
    }

    public RegionDto newRegion(RegionDto region) throws ServiceException {
        return getRegionService().create(region);
    }

    public RegionDto updateRegion(RegionDto region) throws ServiceException {
        return getRegionService().update(region);
    }

    public RegionDto updateRegionByName(RegionDto region) throws ServiceException {
        RegionDto regionByName = getCompositeService().matchByName(region);
        return regionByName != null ? getRegionService().update(regionByName) : getRegionService().create(region);
    }

    public UpdateResultDto deleteRegion(String regionId, String moveChildrenTo) throws ServiceException {
        IRegion region = getRegionService().findByGuid(regionId);
        if (region != null) {
            if (region.getType() == RegionType.ZONE) {
                getZoneService().delete(regionId);
            } else {
                getCompositeService().build(regionId).deleteRegionHierarchy();
            }
        }

        return new UpdateResultDto(UpdateResultDto.Status.SUCCESS.name(), regionId);
    }

    public ZoneDto newZone(ZoneDto zone) {
        return getZoneService().create(zone);
    }

    public ZoneDto updateZone(ZoneDto zone) {
        return getZoneService().update(zone);
    }

    public RadioDto newRadio(RadioDto radio, List<TagDto> tags) throws ServiceException {
        return getRadioService().create(radio, tags);
    }

    public RadioDto updateRadio(RadioDto radio, List<TagDto> tags) throws ServiceException {
        return getRadioService().update(radio, tags);
    }

    public UpdateResultDto deleteRadio(String radioId) {
        getRadioService().delete(radioId);
        return new UpdateResultDto(UpdateResultDto.Status.SUCCESS.name(), radioId);
    }

}
