package com.iscweb.service.composite.leaf;

import com.google.common.collect.Sets;
import com.iscweb.common.model.dto.DeviceActionAccumulator;
import com.iscweb.common.model.dto.DeviceActionResultDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.service.entity.IDoorActionService;

/**
 * DoorComposite represents a door in a composite structure.
 */
public class DoorComposite extends BaseLeafComposite<DoorDto, IDoorActionService> {

    /**
     * Default valueOf implementation for read-only composite.
     * @param dto composite dto part for initialization.
     * @return new composite instance.
     */
    public static DoorComposite valueOf(DoorDto dto) {
        return DoorComposite.valueOf(dto, null);
    }

    /**
     * Factory method to build composite leaf dto element by given door dto.
     * @param dto dto to base properties on.
     * @param service business operations service.
     * @return a new initialized instance of the door composite dto.
     */
    public static DoorComposite valueOf(DoorDto dto, IDoorActionService service) {
        DoorComposite result = new DoorComposite();
        result.setEntityDto(dto);
        result.setService(service);
        return result;
    }

    @Override
    public DeviceActionResultDto emergencyClose(DeviceActionAccumulator accumulator) {
        DeviceActionResultDto result = null;
        super.emergencyClose(accumulator);

        if (accumulator.isRoot()) {
            result = getService().emergencyClose(Sets.newHashSet(this.getEntityDto().getId()));
        } else {
            accumulator.withDevice(this.getEntityDto().getId());
        }

        return result;
    }

    @Override
    public DeviceActionResultDto endEmergency(DeviceActionAccumulator accumulator) {
        DeviceActionResultDto result = null;
        super.endEmergency(accumulator);

        if (accumulator.isRoot()) {
            result = getService().endEmergencyMode(Sets.newHashSet(this.getEntityDto().getId()));
        }

        return result;
    }

    @Override
    public DeviceActionResultDto emergencyOpen(DeviceActionAccumulator accumulator) {
        DeviceActionResultDto result = null;
        super.emergencyOpen(accumulator);

        if (accumulator.isRoot()) {
            result = getService().emergencyOpen(Sets.newHashSet(this.getEntityDto().getId()));
        }

        return result;
    }

    @Override
    public DeviceActionResultDto openDoor(DeviceActionAccumulator accumulator) {
        DeviceActionResultDto result = null;
        super.openDoor(accumulator);

        if (accumulator.isRoot()) {
            result = getService().openDoor(Sets.newHashSet(this.getEntityDto().getId()));
        }

        return result;
    }
}
