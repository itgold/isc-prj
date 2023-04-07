package com.iscweb.service.composite.leaf;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.DeviceActionAccumulator;
import com.iscweb.common.model.dto.DeviceActionResultDto;
import com.iscweb.common.model.dto.composite.BaseRegionComposite;
import com.iscweb.common.model.dto.composite.CompositeFilter;
import com.iscweb.common.model.dto.composite.IRegionComposite;
import com.iscweb.common.model.dto.composite.RegionCompositeType;
import com.iscweb.common.model.dto.entity.BaseSchoolEntityDto;
import com.iscweb.common.model.dto.entity.core.RegionDto;
import com.iscweb.common.service.IDeviceActionHandler;
import com.iscweb.service.entity.IDoorActionService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.iscweb.common.model.dto.composite.RegionCompositeType.CONTAINER;

/**
 * Region composite represents region and a region's logic in a composite structure.
 */
public class RegionComposite extends BaseRegionComposite<RegionDto, IDoorActionService> {

    /**
     * This region's children composite elements.
     */
    @Getter
    @Setter
    private Map<String, IRegionComposite> children = Maps.newLinkedHashMap();

    /**
     * @see IRegionComposite#getCompositeType()
     */
    @Override
    public RegionCompositeType getCompositeType() {
        return CONTAINER;
    }

    /**
     * Default valueOf implementation for read-only composite.
     * @param dto composite dto part for initialization.
     * @return new composite instance.
     */
    public static RegionComposite valueOf(RegionDto dto) {
        return RegionComposite.valueOf(dto, null);
    }

    /**
     * Factory method to create as single composite node with a given region dto initialized.
     *
     * @param region dto to base properties on.
     * @return a new initialized instance of region composite structure.
     */
    public static RegionComposite valueOf(RegionDto region, IDoorActionService service) {
        RegionComposite result = new RegionComposite();
        result.setEntityDto(region);
        result.setService(service);
        return result;
    }

    /**
     * @see IRegionComposite#build(Map, CompositeFilter)
     */
    @Override
    public void build(Map<String, Set<IRegionComposite>> childrenFlat, CompositeFilter filter) {
        // first, initialize all direct children of this region
        Set<IRegionComposite> currentChildren = childrenFlat.get(getEntityDto().getId());
        if (currentChildren != null) {
            inherit(currentChildren, filter);
        }

        // now let all initialized children to initialize itself
        for (IRegionComposite child : getChildren().values()) {
            child.build(childrenFlat, filter);
        }
    }

    /**
     * @see IRegionComposite#toList()
     */
    @Override
    public List<IRegionComposite> toList() {
        List<IRegionComposite> result = Lists.newArrayList();
        for (IRegionComposite child : getChildren().values()) {
            result.addAll(child.toList());
        }
        return result;
    }

    /**
     * @see IRegionComposite#emergencyClose(DeviceActionAccumulator accumulator)
     */
    @Override
    public DeviceActionResultDto emergencyClose(DeviceActionAccumulator accumulator) {
        DeviceActionResultDto result = null;

        for (IRegionComposite child : getChildren().values()) {
            child.emergencyClose(accumulator.childAccumulator());
        }

        if (accumulator.isRoot()) {
            if (CollectionUtils.isEmpty(accumulator.getDeviceIds())) {
                result = DeviceActionResultDto.builder()
                        .status(DeviceActionResultDto.ActionResult.FAILURE)
                        .errors(List.of(new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.UNKNOWN_DEVICE.name(),
                                "No devices found.")))
                        .build();
            } else {
                result = getService().emergencyClose(Sets.newHashSet(accumulator.getDeviceIds()));
            }
        }

        return result;
    }

    /**
     * @see IRegionComposite#endEmergency(DeviceActionAccumulator accumulator)
     */
    @Override
    public DeviceActionResultDto endEmergency(DeviceActionAccumulator accumulator) {
        DeviceActionResultDto result = null;

        for (IRegionComposite child : getChildren().values()) {
            child.emergencyClose(accumulator.childAccumulator());
        }

        if (accumulator.isRoot()) {
            if (CollectionUtils.isEmpty(accumulator.getDeviceIds())) {
                result = DeviceActionResultDto.builder()
                        .status(DeviceActionResultDto.ActionResult.FAILURE)
                        .errors(List.of(new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.UNKNOWN_DEVICE.name(),
                                "No devices found.")))
                        .build();
            } else {
                result = getService().endEmergencyMode(Sets.newHashSet(accumulator.getDeviceIds()));
            }
        }

        return result;
    }

    /**
     * @see IRegionComposite#emergencyOpen(DeviceActionAccumulator accumulator)
     */
    @Override
    public DeviceActionResultDto emergencyOpen(DeviceActionAccumulator accumulator) {
        DeviceActionResultDto result = null;

        for (IRegionComposite child : getChildren().values()) {
            child.emergencyClose(accumulator.childAccumulator());
        }

        if (accumulator.isRoot()) {
            if (CollectionUtils.isEmpty(accumulator.getDeviceIds())) {
                result = DeviceActionResultDto.builder()
                        .status(DeviceActionResultDto.ActionResult.FAILURE)
                        .errors(List.of(new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.UNKNOWN_DEVICE.name(),
                                "No devices found.")))
                        .build();
            } else {
                result = getService().emergencyOpen(Sets.newHashSet(accumulator.getDeviceIds()));
            }
        }

        return result;
    }

    /**
     * @see IRegionComposite#openDoor(DeviceActionAccumulator accumulator)
     */
    @Override
    public DeviceActionResultDto openDoor(DeviceActionAccumulator accumulator) {
        DeviceActionResultDto result = null;

        for (IRegionComposite child : getChildren().values()) {
            child.emergencyClose(accumulator.childAccumulator());
        }

        if (accumulator.isRoot()) {
            if (CollectionUtils.isEmpty(accumulator.getDeviceIds())) {
                result = DeviceActionResultDto.builder()
                        .status(DeviceActionResultDto.ActionResult.FAILURE)
                        .errors(List.of(new DeviceActionResultDto.DeviceActionError(IDeviceActionHandler.ActionError.UNKNOWN_DEVICE.name(),
                                "No devices found.")))
                        .build();
            } else {
                result = getService().openDoor(Sets.newHashSet(accumulator.getDeviceIds()));
            }
        }

        return result;
    }

    /**
     * @see IRegionComposite#save()
     */
    @Override
    public boolean save() {
        throw new IllegalStateException("Not implemented");
    }

    /**
     * @see IRegionComposite#deleteRegionHierarchy()
     */
    @Override
    public boolean deleteRegionHierarchy() throws ServiceException {
        CompositeFilter compositeFilter = CompositeFilter.buildBy(getEntityDto().getId());
        final Pair<Set<String>, Set<IRegionComposite>> regionsAndDevices = collectChildrenOf(compositeFilter);
        String parentId = getEntityDto().getParentIds().stream().findFirst().orElse(null);

        if (parentId != null) {
            Set<IRegionComposite> devices = regionsAndDevices.getSecond();
            for (IRegionComposite device : devices) {
                device.getEntityDto().addParent(parentId);
                device.save();
            }
        }

        Set<String> regions = regionsAndDevices.getFirst();
        for (String regionId : regions) {
            getService().deleteParent(regionId);
        }

        return true;
    }

    /**
     * @see IRegionComposite#collectChildrenOf(CompositeFilter)
     */
    @Override
    public Pair<Set<String>, Set<IRegionComposite>> collectChildrenOf(CompositeFilter filter) {
        Pair<Set<String>, Set<IRegionComposite>> result;

        Set<String> regions = Sets.newHashSet();
        Set<IRegionComposite> devices = Sets.newHashSet();
        result = Pair.of(regions, devices);

        for (IRegionComposite child : getChildren().values()) {
            Pair<Set<String>, Set<IRegionComposite>> childDelete = child.collectChildrenOf(CompositeFilter.buildBy(getEntityDto().getId()));
            regions.addAll(childDelete.getFirst());
            devices.addAll(childDelete.getSecond());
        }

        regions.add(getEntityDto().getId());

        return result;
    }

    /**
     * Appends given list of elements as children to the current composite entity.
     * Applies filtering if provided by filter object.
     *
     * @param children a set of children elements.
     * @param filter   filtering criteria if it needs to be applied.
     */
    public void inherit(Set<IRegionComposite> children, CompositeFilter filter) {
        Map<String, IRegionComposite> collected = children.stream()
                                     .collect(Collectors.toMap(p -> p.getEntityDto().getId(),
                                                                                    composite -> composite)).entrySet()
                                                          .stream()
                                                          .sorted(Map.Entry.comparingByValue())
                                                          .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                                                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        getChildren().putAll(collected);
    }

    /**
     * Orders regions by type in a type order and by name alphanumerically.
     *
     * @param that region composite to make a comparison.
     * @return int value in accordance with Comparable contract.
     * @see Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(@Nonnull IRegionComposite that) {
        int result;

        RegionDto thisDto = this.getEntityDto();
        BaseSchoolEntityDto thatDto = that.getEntityDto();

        if (thatDto instanceof RegionDto) {
            result = thisDto.compareTo((RegionDto) thatDto);
        } else {
            result = -1;
        }

        return result;
    }
}
