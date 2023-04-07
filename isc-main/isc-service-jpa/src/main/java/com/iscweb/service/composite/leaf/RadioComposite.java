package com.iscweb.service.composite.leaf;

import com.iscweb.common.model.dto.entity.core.RadioDto;
import com.iscweb.service.entity.IRadioActionService;

/**
 * RadioComposite represents a radio entity in a composite structure.
 */
public class RadioComposite extends BaseLeafComposite<RadioDto, IRadioActionService> {

    /**
     * Default valueOf implementation for read-only composite.
     * @param dto composite dto part for initialization.
     * @return new composite instance.
     */
    public static RadioComposite valueOf(RadioDto dto) {
        return RadioComposite.valueOf(dto, null);
    }

    /**
     * Factory method to build composite element by given camera dto.
     * @param dto dto to base properties on.
     * @param service business operations service.
     * @return a new initialized instance of the camera composite dto.
     */
    public static RadioComposite valueOf(RadioDto dto, IRadioActionService service) {
        RadioComposite result = new RadioComposite();
        result.setEntityDto(dto);
        result.setService(service);
        return result;
    }
}
