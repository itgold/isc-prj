package com.iscweb.service.composite.leaf;

import com.google.common.collect.Lists;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.DeviceActionAccumulator;
import com.iscweb.common.model.dto.DeviceActionResultDto;
import com.iscweb.common.model.dto.composite.BaseRegionComposite;
import com.iscweb.common.model.dto.composite.CompositeFilter;
import com.iscweb.common.model.dto.composite.IRegionComposite;
import com.iscweb.common.model.dto.composite.RegionCompositeType;
import com.iscweb.common.model.dto.entity.BaseExternalEntityDto;
import com.iscweb.common.model.dto.entity.BaseSchoolEntityDto;
import com.iscweb.service.entity.IDeviceActionService;
import org.springframework.data.util.Pair;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is a base terminal(leaf) composite element.
 * It has no children and terminates a composite branch.
 * @param <D> represents the type of leaf entity composite dto.
 * @param <S> service that is responsible for business operations.
 */
public abstract class BaseLeafComposite<D extends BaseExternalEntityDto, S extends IDeviceActionService> extends BaseRegionComposite<D, S> {

    /**
     * @see IRegionComposite#getCompositeType()
     */
    @Override
    public RegionCompositeType getCompositeType() {
        return RegionCompositeType.LEAF;
    }

    /**
     * @see IRegionComposite#build(Map, CompositeFilter)
     */
    @Override
    public void build(Map<String, Set<IRegionComposite>> children, CompositeFilter filter) {
        //this is a terminal element. Do nothing.
    }

    /**
     * @see IRegionComposite#toList()
     */
    @Override
    public List<IRegionComposite> toList() {
        return Lists.newArrayList(this);
    }

    /**
     * @see IRegionComposite#emergencyClose(DeviceActionAccumulator accumulator)
     */
    @Override
    public DeviceActionResultDto emergencyClose(DeviceActionAccumulator accumulator) {
        if (getService() == null) {
            throw new IllegalStateException("Composite was not initialized to execute business operations");
        }

        return null;
    }

    /**
     * @see IRegionComposite#endEmergency(DeviceActionAccumulator accumulator)
     */
    @Override
    public DeviceActionResultDto endEmergency(DeviceActionAccumulator accumulator) {
        if (getService() == null) {
            throw new IllegalStateException("Composite was not initialized to execute business operations");
        }

        return null;
    }

    /**
     * @see IRegionComposite#emergencyOpen(DeviceActionAccumulator accumulator)
     */
    @Override
    public DeviceActionResultDto emergencyOpen(DeviceActionAccumulator accumulator) {
        if (getService() == null) {
            throw new IllegalStateException("Composite was not initialized to execute business operations");
        }

        return null;
    }

    /**
     * @see IRegionComposite#openDoor(DeviceActionAccumulator accumulator)
     */
    @Override
    public DeviceActionResultDto openDoor(DeviceActionAccumulator accumulator) {
        if (getService() == null) {
            throw new IllegalStateException("Composite was not initialized to execute business operations");
        }

        return null;
    }

    /**
     * @see IRegionComposite#save()
     */
    @Override
    public boolean save() throws ServiceException {
        return getService().save(getEntityDto());
    }

    /**
     * @see IRegionComposite#deleteRegionHierarchy()
     */
    @Override
    public boolean deleteRegionHierarchy() {
        return false;
    }

    /**
     * @see IRegionComposite#collectChildrenOf(CompositeFilter)
     */
    @Override
    public Pair<Set<String>, Set<IRegionComposite>> collectChildrenOf(CompositeFilter filter) {
        getEntityDto().removeParent(filter.getRegionGuid());
        return Pair.of(Collections.emptySet(), Collections.singleton(this));
    }

    /**
     * Orders devices by type and then by name alphanumerically.
     * @param that region leaf composite to make a comparison.
     * @return int value in accordance with Comparable contract.
     *
     * @see Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(@NotNull IRegionComposite that) {
        int result = 1;

        D thisDto = this.getEntityDto();
        BaseSchoolEntityDto thatDto = that.getEntityDto();

        if (thatDto instanceof BaseExternalEntityDto) {
            result = thisDto.compareTo((BaseExternalEntityDto) thatDto);
        }

        return result;
    }
}
