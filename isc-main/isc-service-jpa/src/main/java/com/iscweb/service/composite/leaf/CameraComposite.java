package com.iscweb.service.composite.leaf;

import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.service.entity.ICameraActionService;

/**
 * CameraComposite represents a camera in a composite structure.
 */
public class CameraComposite extends BaseLeafComposite<CameraDto, ICameraActionService> {

    /**
     * Default valueOf implementation for read-only composite.
     * @param dto composite dto part for initialization.
     * @return new composite instance.
     */
    public static CameraComposite valueOf(CameraDto dto) {
        return CameraComposite.valueOf(dto, null);
    }

    /**
     * Factory method to build composite element by given camera dto.
     * @param dto dto to base properties on.
     * @param service business operations service.
     * @return a new initialized instance of the camera composite dto.
     */
    public static CameraComposite valueOf(CameraDto dto, ICameraActionService service) {
        CameraComposite result = new CameraComposite();
        result.setEntityDto(dto);
        result.setService(service);
        return result;
    }
}
