package com.iscweb.service.composite.leaf;

import com.iscweb.common.model.dto.entity.core.SpeakerDto;
import com.iscweb.service.entity.ISpeakerActionService;

/**
 * DroneComposite represents a drone entity in a composite structure.
 */
public class SpeakerComposite extends BaseLeafComposite<SpeakerDto, ISpeakerActionService> {

    /**
     * Default valueOf implementation for read-only composite.
     * @param dto composite dto part for initialization.
     * @return new composite instance.
     */
    public static SpeakerComposite valueOf(SpeakerDto dto) {
        return SpeakerComposite.valueOf(dto, null);
    }

    /**
     * Factory method to build composite element by given camera dto.
     * @param dto dto to base properties on.
     * @param service business operations service.
     * @return a new initialized instance of the camera composite dto.
     */
    public static SpeakerComposite valueOf(SpeakerDto dto, ISpeakerActionService service) {
        SpeakerComposite result = new SpeakerComposite();
        result.setEntityDto(dto);
        result.setService(service);
        return result;
    }
}
