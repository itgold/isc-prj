package com.iscweb.service.composite.leaf;

import com.iscweb.common.model.dto.entity.core.SafetyDto;
import com.iscweb.service.entity.ISafetyActionService;

/**
 * SafetyComposite represents a safety entity in a composite structure.
 */
public class SafetyComposite extends BaseLeafComposite<SafetyDto, ISafetyActionService> {

    /**
     * Default valueOf implementation for read-only composite.
     * @param dto composite dto part for initialization.
     * @return new composite instance.
     */
    public static SafetyComposite valueOf(SafetyDto dto) {
        return SafetyComposite.valueOf(dto, null);
    }

    /**
     * Factory method to build composite element by given safety dto.
     * @param dto dto to base properties on.
     * @param service business operations service.
     * @return a new initialized instance of the safety composite dto.
     */
    public static SafetyComposite valueOf(SafetyDto dto, ISafetyActionService service) {
        SafetyComposite result = new SafetyComposite();
        result.setEntityDto(dto);
        result.setService(service);
        return result;
    }
}
