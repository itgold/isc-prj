package com.iscweb.service.composite.impl;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.ISchoolEntity;
import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.composite.CompositeFilter;
import com.iscweb.common.model.dto.composite.IRegionComposite;
import com.iscweb.common.model.dto.composite.RegionCompositeType;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.dto.entity.core.DroneDto;
import com.iscweb.common.model.dto.entity.core.RadioDto;
import com.iscweb.common.model.dto.entity.core.RegionDto;
import com.iscweb.common.model.dto.entity.core.SafetyDto;
import com.iscweb.common.model.dto.entity.core.SpeakerDto;
import com.iscweb.common.model.dto.entity.core.UtilityDto;
import com.iscweb.persistence.model.BaseJpaCompositeEntity;
import com.iscweb.persistence.model.jpa.RegionJpa;
import com.iscweb.service.composite.leaf.CameraComposite;
import com.iscweb.service.composite.leaf.DoorComposite;
import com.iscweb.service.composite.leaf.DroneComposite;
import com.iscweb.service.composite.leaf.RadioComposite;
import com.iscweb.service.composite.leaf.RegionComposite;
import com.iscweb.service.composite.leaf.SafetyComposite;
import com.iscweb.service.composite.leaf.SpeakerComposite;
import com.iscweb.service.composite.leaf.UtilityComposite;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import com.iscweb.service.entity.CameraEntityService;
import com.iscweb.service.entity.DoorEntityService;
import com.iscweb.service.entity.DroneEntityService;
import com.iscweb.service.entity.ICameraActionService;
import com.iscweb.service.entity.IDoorActionService;
import com.iscweb.service.entity.IDroneActionService;
import com.iscweb.service.entity.IRadioActionService;
import com.iscweb.service.entity.ISafetyActionService;
import com.iscweb.service.entity.ISpeakerActionService;
import com.iscweb.service.entity.IUtilityActionService;
import com.iscweb.service.entity.RadioEntityService;
import com.iscweb.service.entity.RegionEntityService;
import com.iscweb.service.entity.SafetyEntityService;
import com.iscweb.service.entity.SpeakerEntityService;
import com.iscweb.service.entity.UtilityEntityService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Factory class for building regions composite structure.
 *
 * @author skurenkov
 */
@Slf4j
@Component
public class CompositeFactory {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RegionEntityService regionEntityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DoorEntityService doorEntityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CameraEntityService cameraEntityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DroneEntityService droneEntityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SpeakerEntityService speakerEntityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SafetyEntityService safetyEntityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private UtilityEntityService utilityEntityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RadioEntityService radioEntityService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ICameraActionService cameraActionService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IDoorActionService doorActionService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IDroneActionService droneActionService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ISpeakerActionService speakerActionService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IRadioActionService radioActionService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private ISafetyActionService safetyActionService;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private IUtilityActionService utilityActionService;

    /**
     * Composite factory for composite building by provided root region id and write support flag.
     * No filtering is getting applied.
     *
     * @param regionGuid   root region id.
     * @return constructed composite instance.
     */
    public IRegionComposite build(String regionGuid) {
        return build(regionGuid, CompositeFilter.build());
    }

    /**
     * Primary method for building composite tree by a given root region guid.
     *
     * @param regionGuid   root region global identifier.
     * @param filter       composite filtering parameters.
     * @return composite structure constructed for a given region.
     */
    public IRegionComposite build(String regionGuid, CompositeFilter filter) {
        IRegionComposite result = new RegionComposite();

        if (regionGuid != null && !regionGuid.isEmpty()) {
            RegionJpa rootRegion = (RegionJpa) getRegionEntityService().findByGuid(regionGuid,
                    Arrays.asList(LazyLoadingField.PARENT_REGION, LazyLoadingField.METADATA));
            if (rootRegion != null) {
                result = buildRegionCompositeNode(Convert.my(rootRegion).scope(Scope.ALL).boom());
                
                // all regions are needed for future devices querying by all regions in a single query.
                Set<RegionJpa> allRegions = Sets.newHashSet();
                allRegions.add(rootRegion);
                Set<RegionJpa> childrenRegions = findChildren(rootRegion);
                allRegions.addAll(childrenRegions);

                //resulting composite elements map
                Map<String, Set<IRegionComposite>> compositeElementsMap = Maps.newHashMap();

                //populate composite regions
                childrenRegions.forEach(region -> {
                    RegionDto regionDto = Convert.my(region).scope(Scope.ALL).boom();
                    RegionComposite regionComposite = buildRegionCompositeNode(regionDto);

                    //add current region to the map
                    compositeElementsMap.put(region.getGuid(),
                                             Optional.ofNullable(compositeElementsMap.get(region.getGuid()))
                                                     .orElse(Sets.newHashSet()));

                    //associate current region with parent regions in the map
                    region.getRegions()
                          .forEach(parentRegion -> {
                              Set<IRegionComposite> parent = compositeElementsMap.computeIfAbsent(parentRegion.getGuid(), s -> Sets.newHashSet());
                              boolean exists = parent.stream().anyMatch(p -> Objects.equals(p.getEntityDto().getId(), regionComposite.getEntityDto().getId()));
                              if (!exists) {
                                  parent.add(regionComposite);
                              }
                          });
                });

                // populate composite leaves. Specific device type at a time
                Set<? extends ISchoolEntity> schoolDevices = findDirectChildren(allRegions, filter);
                updateElementsMap(schoolDevices, compositeElementsMap);

                result.build(compositeElementsMap, filter);
            }
        }

        return result;
    }

    public Set<BaseJpaCompositeEntity> findDirectChildren(String regionGuid, CompositeFilter filter) {
        Set<BaseJpaCompositeEntity> entities = Sets.newHashSet();

        RegionJpa rootRegion = (RegionJpa) getRegionEntityService().findByGuid(regionGuid,
                Arrays.asList(LazyLoadingField.PARENT_REGION, LazyLoadingField.METADATA));
        Set<RegionJpa> childrenRegions = findChildren(rootRegion);
        entities.addAll(childrenRegions);

        Set<RegionJpa> allRegionIds = Sets.newHashSet(rootRegion);
        Set<? extends ISchoolEntity> schoolDevices = findDirectChildren(allRegionIds, filter);
        if (!CollectionUtils.isEmpty(schoolDevices)) {
            schoolDevices.forEach(entity -> entities.add((BaseJpaCompositeEntity) entity));
        }

        return entities;
    }

    protected Set<BaseJpaCompositeEntity> findDirectChildren(Set<RegionJpa> allRegionIds, CompositeFilter filter) {
        Set<BaseJpaCompositeEntity> entities = Sets.newHashSet();

        if (filter.enabled(RegionCompositeType.LEAF)) {
            if (filter.enabled(EntityType.DOOR)) {
                Set<? extends ISchoolEntity> schoolDevices = getDoorEntityService().findByRegions(allRegionIds,
                        Arrays.asList(LazyLoadingField.PARENT_REGION, LazyLoadingField.DEVICE_STATE));
                if (!CollectionUtils.isEmpty(schoolDevices)) {
                    schoolDevices.forEach(entity -> entities.add((BaseJpaCompositeEntity) entity));
                }
            }

            if (filter.enabled(EntityType.CAMERA)) {
                Set<? extends ISchoolEntity> schoolDevices = getCameraEntityService().findByRegions(allRegionIds,
                        Arrays.asList(LazyLoadingField.PARENT_REGION, LazyLoadingField.DEVICE_STATE));
                if (!CollectionUtils.isEmpty(schoolDevices)) {
                    schoolDevices.forEach(entity -> entities.add((BaseJpaCompositeEntity) entity));
                }
            }

            if (filter.enabled(EntityType.DRONE)) {
                Set<? extends ISchoolEntity> schoolDevices = getDroneEntityService().findByRegions(allRegionIds,
                        Arrays.asList(LazyLoadingField.PARENT_REGION, LazyLoadingField.DEVICE_STATE));
                if (!CollectionUtils.isEmpty(schoolDevices)) {
                    schoolDevices.forEach(entity -> entities.add((BaseJpaCompositeEntity) entity));
                }
            }

            if (filter.enabled(EntityType.SPEAKER)) {
                Set<? extends ISchoolEntity> schoolDevices = getSpeakerEntityService().findByRegions(allRegionIds,
                        Arrays.asList(LazyLoadingField.PARENT_REGION, LazyLoadingField.DEVICE_STATE));
                if (!CollectionUtils.isEmpty(schoolDevices)) {
                    schoolDevices.forEach(entity -> entities.add((BaseJpaCompositeEntity) entity));
                }
            }
            if (filter.enabled(EntityType.UTILITY)) {
                Set<? extends ISchoolEntity> schoolDevices = getUtilityEntityService().findByRegions(allRegionIds,
                        Arrays.asList(LazyLoadingField.PARENT_REGION, LazyLoadingField.DEVICE_STATE));
                if (!CollectionUtils.isEmpty(schoolDevices)) {
                    schoolDevices.forEach(entity -> entities.add((BaseJpaCompositeEntity) entity));
                }
            }
            if (filter.enabled(EntityType.SAFETY)) {
                Set<? extends ISchoolEntity> schoolDevices = getSafetyEntityService().findByRegions(allRegionIds,
                        Arrays.asList(LazyLoadingField.PARENT_REGION, LazyLoadingField.DEVICE_STATE));
                if (!CollectionUtils.isEmpty(schoolDevices)) {
                    schoolDevices.forEach(entity -> entities.add((BaseJpaCompositeEntity) entity));
                }
            }
            if (filter.enabled(EntityType.RADIO)) {
                Set<? extends ISchoolEntity> schoolDevices = getRadioEntityService().findByRegions(allRegionIds,
                        Arrays.asList(LazyLoadingField.PARENT_REGION, LazyLoadingField.DEVICE_STATE));
                if (!CollectionUtils.isEmpty(schoolDevices)) {
                    schoolDevices.forEach(entity -> entities.add((BaseJpaCompositeEntity) entity));
                }
            }
        }

        return entities;
    }

    /**
     * Recursive function that loads up all the child regions for a given root region.
     *
     * @param rootRegion parent region to fetch children for.
     * @return a set of children not in any particular structure, just a Set.
     */
    private Set<RegionJpa> findChildren(RegionJpa rootRegion) {
        Set<RegionJpa> result = Sets.newHashSet();
        if (rootRegion != null) {
            Set<RegionJpa> children = getRegionEntityService().findByRegion(rootRegion,
                    Arrays.asList(LazyLoadingField.PARENT_REGION, LazyLoadingField.METADATA));
            if (children.size() > 0) {
                for (RegionJpa child : children) {
                    result.add(child);
                    result.addAll(findChildren(child));
                }
            }
        }

        return result;
    }

    /**
     * Create a composite elements map with a given set of school devices.
     *
     * @param schoolDevices school devices to save to map.
     */
    private void updateElementsMap(Set<? extends ISchoolEntity> schoolDevices,
                                   Map<String, Set<IRegionComposite>> compositeElementsMap) {

        schoolDevices.forEach(entity -> {
            IExternalEntityDto dto = Convert.my(entity).scope(Scope.ALL).boom();
            IRegionComposite dtoComposite = buildDeviceCompositeNode(dto);
            entity.getRegions()
                  .forEach(parentRegion -> compositeElementsMap.computeIfAbsent(parentRegion.getGuid(), s -> Sets.newHashSet())
                                                               .add(dtoComposite));
        });
    }

    /**
     * Constructs a composite object instance based on the provided dto's device type.
     *
     * @param dto          of a supported type.
     * @return a new instance of a composite object.
     */
    public IRegionComposite buildDeviceCompositeNode(IExternalEntityDto dto) {
        IRegionComposite result;

        switch (dto.getEntityType()) {
            case DOOR:
                result = DoorComposite.valueOf((DoorDto) dto, getDoorActionService());
                break;
            case CAMERA:
                result = CameraComposite.valueOf((CameraDto) dto, getCameraActionService());
                break;
            case DRONE:
                result = DroneComposite.valueOf((DroneDto) dto, getDroneActionService());
                break;
            case SPEAKER:
                result = SpeakerComposite.valueOf((SpeakerDto) dto, getSpeakerActionService());
                break;
            case UTILITY:
                result = UtilityComposite.valueOf((UtilityDto) dto, getUtilityActionService());
                break;
            case SAFETY:
                result = SafetyComposite.valueOf((SafetyDto) dto, getSafetyActionService());
                break;
            case RADIO:
                result = RadioComposite.valueOf((RadioDto) dto, getRadioActionService());
                break;
            default:
                throw new IllegalArgumentException("Invalid device type provided for composite construction");
        }

        return result;
    }

    public RegionComposite buildRegionCompositeNode(RegionDto dto) {
        RegionComposite result;

        result = RegionComposite.valueOf(dto, getDoorActionService());

        return result;
    }
}
