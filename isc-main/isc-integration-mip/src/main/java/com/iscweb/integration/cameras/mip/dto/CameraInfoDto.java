package com.iscweb.integration.cameras.mip.dto;

import com.iscweb.common.model.dto.IDto;
import com.mip.command.CameraGroupInfo;
import com.mip.command.CameraInfo;
import com.mip.recorderStatus.CameraDeviceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CameraInfoDto implements IDto {

    private static final String SCHEMA_HTTPS = "HTTPS";

    private String cameraId;
    private CameraInfo info;
    private CameraGroupInfo group;
    private CameraDeviceStatus status;
    private URI recorderService;

    public String getCameraServiceHost() {
        return recorderService != null ? recorderService.getHost() : null;
    }

    public int getCameraServicePort() {
        return recorderService != null ? recorderService.getPort() : 0;
    }

    public boolean isCameraServiceSsl() {
        return recorderService != null ? SCHEMA_HTTPS.equalsIgnoreCase(recorderService.getScheme()) : false;
    }
}
