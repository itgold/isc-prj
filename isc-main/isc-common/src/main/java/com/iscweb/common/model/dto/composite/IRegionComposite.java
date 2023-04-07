package com.iscweb.common.model.dto.composite;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.dto.DeviceActionAccumulator;
import com.iscweb.common.model.dto.DeviceActionResultDto;
import com.iscweb.common.model.dto.IDto;
import com.iscweb.common.model.dto.entity.BaseSchoolEntityDto;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is the primary interface in a regions composite structure.
 */
public interface IRegionComposite extends IDto, Comparable<IRegionComposite> {

    /**
     * Default guid for the root element.
     */
    String ROOT = "00000000-0000-0000-0000-000000000000";

    /**
     * Initializer method for constructing composite structure based on a map of
     * composite objects that are passed in as a parameter.
     * @param regionDtos a map of composite objects grouped by parent region identifier.
     * @param filter     composite filter for filtering out the result.
     */
    void build(Map<String, Set<IRegionComposite>> regionDtos, CompositeFilter filter);

    /**
     * Flattens down the tree structure into the flat list of composite leafs.
     * @return a list of terminal elements for this composite.
     */
    List<IRegionComposite> toList();

    /**
     * This is an indicator of a particular composite instance type.
     * @return the type of a current composite object.
     */
    RegionCompositeType getCompositeType();

    /**
     * Getter declaration for the dto object of this composite instance.
     * @param <D> school entity dto type.
     * @return an instance of declared dto.
     */
    <D extends BaseSchoolEntityDto> D getEntityDto();

    /*=================      Device Actions     =================*/

    /**
     * This method is called in case of a lock-down event for a specific region / device.
     * Particular composite implementation is responsible for the actions it should take for a specific event.
     * Overwrite this method and implement the logic of handling action in a specific composite implementation.
     * @return true if operation succeeded.
     */
    DeviceActionResultDto emergencyClose(DeviceActionAccumulator deviceActionAccumulator);

    /**
     * This method is called in case of a canceling emergency mode event for a specific region / device.
     * Particular composite implementation is responsible for the actions it should take for a specific event.
     * Overwrite this method and implement the logic of handling action in a specific composite implementation.
     * @return true if operation succeeded.
     */
    DeviceActionResultDto endEmergency(DeviceActionAccumulator build);

    /**
     * This method is called in case of an emergency open event for a specific region / device.
     * Particular composite implementation is responsible for the actions it should take for a specific event.
     * Overwrite this method and implement the logic of handling action in a specific composite implementation.
     * @return true if operation succeeded.
     */
    DeviceActionResultDto emergencyOpen(DeviceActionAccumulator build);

    /**
     * This method is called in case of open door event for a specific region / device.
     * Particular composite implementation is responsible for the actions it should take for a specific event.
     * Overwrite this method and implement the logic of handling action in a specific composite implementation.
     * @return true if operation succeeded.
     */
    DeviceActionResultDto openDoor(DeviceActionAccumulator build);

    /**
     * Used to save current composite entry state to database.
     * @return true if save operation succeed.
     */
    boolean save() throws ServiceException;

    /**
     * Deletes current composite region entry and all of its children regions from database.
     * All remaining orphan devices will be associated with the parent of current region.
     * @return true if operation succeed.
     */
    boolean deleteRegionHierarchy() throws ServiceException;

    /**
     * Recursive method that is used to collect regions and devices that are
     * direct or indirect children of a parent region provided by filter.
     * @param filter provides parent region guid.
     * @return a pair of regions and devices. First element is a set of region guids, second is a set of devices.
     */
    Pair<Set<String>, Set<IRegionComposite>> collectChildrenOf(CompositeFilter filter);
}
