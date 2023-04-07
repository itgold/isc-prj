package com.iscweb.integration.cameras.mock.dto;

import com.iscweb.common.model.dto.IDto;
import com.iscweb.common.model.dto.entity.core.CameraGroupDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CameraInfoDto implements IDto {

    private static final String SCHEMA_HTTPS = "HTTPS";

    private String cameraId;
    private CameraGroupDto group;
    private String name;
    private String description;
    private String videoPath;
}
