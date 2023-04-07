package com.iscweb.component.web.controller.graphql.queries;

import com.google.common.collect.Lists;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.QueryFilterDto;
import com.iscweb.common.model.dto.composite.CompositeFilter;
import com.iscweb.common.model.dto.composite.IRegionComposite;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.model.dto.entity.core.CameraSearchResultDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.dto.entity.core.DoorSearchResultDto;
import com.iscweb.common.model.dto.entity.core.DroneDto;
import com.iscweb.common.model.dto.entity.core.DroneSearchResultDto;
import com.iscweb.common.model.dto.entity.core.RadioDto;
import com.iscweb.common.model.dto.entity.core.RadioSearchResultDto;
import com.iscweb.common.model.dto.entity.core.RegionDto;
import com.iscweb.common.model.dto.entity.core.RegionSearchResultDto;
import com.iscweb.common.model.dto.entity.core.SafetyDto;
import com.iscweb.common.model.dto.entity.core.SafetySearchResultDto;
import com.iscweb.common.model.dto.entity.core.SpeakerDto;
import com.iscweb.common.model.dto.entity.core.SpeakerSearchResultDto;
import com.iscweb.common.model.dto.entity.core.UtilityDto;
import com.iscweb.common.model.dto.entity.core.UtilitySearchResultDto;
import com.iscweb.common.model.dto.entity.core.ZoneSearchResultDto;
import com.iscweb.common.service.integration.camera.ICameraService;
import com.iscweb.component.web.controller.graphql.common.PageRequestDto;
import com.iscweb.component.web.controller.graphql.common.SortOrderDto;
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
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.iscweb.component.web.util.GraphQlUtils.sortingPage;

/**
 * Generic GraphQL query resolver for school entities.
 */
@Slf4j
@Component
public class GraphQlSchoolEntityQuery implements GraphQLQueryResolver {

    public static final PageRequest DEFAULT_PAGE = PageRequest.of(0, 10, Sort.unsorted());

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DoorService doorService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CameraService cameraService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ICameraService cameraDetailsService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DroneService droneService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SpeakerService speakerService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SafetyService safetyService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RegionService regionService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ZoneService zoneService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private UtilityService utilityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RadioService radioService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CompositeService compositeService;

    public List<DoorDto> doors(PageRequestDto page, List<SortOrderDto> sort, DataFetchingEnvironment env) {
        // List<ProjectionDto> columns = GraphQlUtils.queryFieldsSelection(env);
        // return getDoorService().findAll(columns, sortingPage(page, sort, DEFAULT_PAGE));

        return getDoorService().findAll(sortingPage(page, sort, DEFAULT_PAGE));
    }

    public List<IRegionComposite> doorsByRegion(String regionId, PageRequestDto page, List<SortOrderDto> sort) {
        return Lists.newArrayList(getCompositeService().findChildren(regionId, CompositeFilter.buildBy(EntityType.DOOR)));
    }

    public DoorSearchResultDto queryDoors(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return getDoorService().findDoors(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }

    public List<UtilityDto> utilities(PageRequestDto page, List<SortOrderDto> sort, DataFetchingEnvironment env) {
        return getUtilityService().findAll(sortingPage(page, sort, DEFAULT_PAGE));
    }

    public List<IRegionComposite> utilitiesByRegion(String regionId, PageRequestDto page, List<SortOrderDto> sort) {
        return Lists.newArrayList(getCompositeService().findChildren(regionId, CompositeFilter.buildBy(EntityType.UTILITY)));
    }

    public UtilitySearchResultDto queryUtilities(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return (UtilitySearchResultDto) getUtilityService().findUtilities(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }

    public List<CameraDto> cameras(PageRequestDto page, List<SortOrderDto> sort, DataFetchingEnvironment env) {
        List<CameraDto> cameras;
        /*
        List<ProjectionDto> columns = GraphQlUtils.queryFieldsSelection(env);
        cameras = getCameraService().findAll(columns, sortingPage(page, sort, DEFAULT_PAGE));
        if (!CollectionUtils.isEmpty(cameras)) {
            cameras = cameras.stream()
                             .map(camera -> getCameraDetailsService().getCameraDetails(camera))
                             .collect(Collectors.toList());
        }
        */
        cameras = getCameraService().findAll(sortingPage(page, sort, DEFAULT_PAGE));
        return cameras;
    }

    public List<CameraDto> camerasByRegion(String regionId, PageRequestDto page, List<SortOrderDto> sort, DataFetchingEnvironment env) {
        List<CameraDto> result = Lists.newArrayList();

        //todo sk: return camera composite
        List<IRegionComposite> cameras = Lists.newArrayList(getCompositeService().findChildren(regionId, CompositeFilter.buildBy(EntityType.CAMERA)));
        if (!CollectionUtils.isEmpty(cameras)) {
            result = cameras.stream()
                             .map(camera -> {
                                 CameraDto compositeDto = camera.getEntityDto();
                                 return getCameraDetailsService().getCameraDetails(compositeDto);
                             })
                             .collect(Collectors.toList());
        }

        return result;
    }

    public CameraSearchResultDto queryCameras(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return getCameraService().findCameras(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }

    public List<SpeakerDto> speakers(PageRequestDto page, List<SortOrderDto> sort, DataFetchingEnvironment env) {
        List<SpeakerDto> speakers;

        // List<ProjectionDto> columns = GraphQlUtils.queryFieldsSelection(env);
        // speakers = getSpeakerService().findAll(columns, sortingPage(page, sort, DEFAULT_PAGE));
        speakers = getSpeakerService().findAll(sortingPage(page, sort, DEFAULT_PAGE));
        return speakers;
    }

    public List<IRegionComposite> speakersBySchool(String schoolId, PageRequestDto page, List<SortOrderDto> sort, DataFetchingEnvironment env) {
        return Lists.newArrayList(getCompositeService().findChildren(schoolId, CompositeFilter.buildBy(EntityType.SPEAKER)));
    }

    public SpeakerSearchResultDto querySpeakers(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return getSpeakerService().findSpeakers(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }

    public List<DroneDto> drones(PageRequestDto page, List<SortOrderDto> sort, DataFetchingEnvironment env) {
        // List<ProjectionDto> columns = GraphQlUtils.queryFieldsSelection(env);
        // return getDroneService().findAll(columns, sortingPage(page, sort, DEFAULT_PAGE));

        return getDroneService().findAll(sortingPage(page, sort, DEFAULT_PAGE));
    }

    public List<IRegionComposite> dronesBySchool(String schoolId, PageRequestDto page, List<SortOrderDto> sort, DataFetchingEnvironment env) {
        return Lists.newArrayList(getCompositeService().findChildren(schoolId, CompositeFilter.buildBy(EntityType.DRONE)));
    }

    public DroneSearchResultDto queryDrones(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return getDroneService().findDrones(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }

    public List<SafetyDto> safeties(PageRequestDto page, List<SortOrderDto> sort, DataFetchingEnvironment env) {
        return getSafetyService().findAll(sortingPage(page, sort, DEFAULT_PAGE));
    }

    public List<IRegionComposite> safetiesByRegion(String regionId, PageRequestDto page, List<SortOrderDto> sort) {
        return Lists.newArrayList(getCompositeService().findChildren(regionId, CompositeFilter.buildBy(EntityType.SAFETY)));
    }

    public SafetySearchResultDto querySafeties(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return (SafetySearchResultDto) getSafetyService().findSafeties(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }

    public List<RegionDto> regions(PageRequestDto page, List<SortOrderDto> sort, DataFetchingEnvironment env) {
        // List<ProjectionDto> columns = GraphQlUtils.queryFieldsSelection(env);
        // return getRegionService().findAll(columns, sortingPage(page, sort, DEFAULT_PAGE));
        return getRegionService().findAll(sortingPage(page, sort, DEFAULT_PAGE));
    }

    public RegionSearchResultDto queryRegions(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return getRegionService().findRegions(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }

    public ZoneSearchResultDto queryZones(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return (ZoneSearchResultDto) getZoneService().findZones(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }

    public List<RadioDto> radios(PageRequestDto page, List<SortOrderDto> sort) {
        return getRadioService().findAll(sortingPage(page, sort, DEFAULT_PAGE));
    }

    public List<IRegionComposite> radiosByRegion(String regionId, PageRequestDto page, List<SortOrderDto> sort) {
        return Lists.newArrayList(getCompositeService().findChildren(regionId, CompositeFilter.buildBy(EntityType.RADIO)));
    }

    public RadioSearchResultDto queryRadios(QueryFilterDto filter, PageRequestDto page, List<SortOrderDto> sort) {
        return getRadioService().findRadios(filter, sortingPage(page, sort, DEFAULT_PAGE));
    }
}
