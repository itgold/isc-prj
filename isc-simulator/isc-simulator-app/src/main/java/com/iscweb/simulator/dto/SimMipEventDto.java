package com.iscweb.simulator.dto;

import com.iscweb.common.model.dto.IDto;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.integration.cameras.mip.services.streaming.dto.LiveStatusItem;
import lombok.Data;

@Data
public class SimMipEventDto implements IDto {

    private static final String INPUT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private String deviceId;
    private LiveStatusItem.StatusType mipOperation;

    private CameraDto cameraDto;

}
