package com.iscweb.service.composite.leaf;

import com.iscweb.common.model.dto.entity.core.DroneDto;
import com.iscweb.service.entity.IDroneActionService;

/**
 * DroneComposite represents a drone entity in a composite structure.
 */
public class DroneComposite extends BaseLeafComposite<DroneDto, IDroneActionService> {

    /**
     * Default valueOf implementation for read-only composite.
     * @param dto composite dto part for initialization.
     * @return new composite instance.
     */
    public static DroneComposite valueOf(DroneDto dto) {
        return DroneComposite.valueOf(dto, null);
    }

    /**
     * Factory method to build composite element by given camera dto.
     * @param dto dto to base properties on.
     * @param service business operations service.
     * @return a new initialized instance of the camera composite dto.
     */
    public static DroneComposite valueOf(DroneDto dto, IDroneActionService service) {
        DroneComposite result = new DroneComposite();
        result.setEntityDto(dto);
        result.setService(service);
        return result;
    }
}
