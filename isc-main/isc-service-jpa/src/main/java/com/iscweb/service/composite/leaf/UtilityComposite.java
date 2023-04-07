package com.iscweb.service.composite.leaf;

import com.iscweb.common.model.dto.entity.core.UtilityDto;
import com.iscweb.service.entity.IUtilityActionService;

/**
 * UtilityComposite represents a drone entity in a composite structure.
 */
public class UtilityComposite extends BaseLeafComposite<UtilityDto, IUtilityActionService> {

    /**
     * Default valueOf implementation for read-only composite.
     * @param dto composite dto part for initialization.
     * @return new composite instance.
     */
    public static UtilityComposite valueOf(UtilityDto dto) {
        return UtilityComposite.valueOf(dto, null);
    }

    /**
     * Factory method to build composite element by given camera dto.
     * @param dto dto to base properties on.
     * @param service business operations service.
     * @return a new initialized instance of the camera composite dto.
     */
    public static UtilityComposite valueOf(UtilityDto dto, IUtilityActionService service) {
        UtilityComposite result = new UtilityComposite();
        result.setEntityDto(dto);
        result.setService(service);
        return result;
    }
}
