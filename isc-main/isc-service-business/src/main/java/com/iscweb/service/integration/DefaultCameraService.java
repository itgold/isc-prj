package com.iscweb.service.integration;

import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.service.integration.camera.ICameraService;
import com.iscweb.common.service.integration.camera.ICameraStream;

public class DefaultCameraService implements ICameraService {

    @Override
    public String serviceInfo() {
        return "NO CAMERA SERVICE AVAILABLE";
    }

    @Override
    public ICameraStream streamVideo(String cameraId, String streamId) {
        return null;
    }

    @Override
    public CameraDto getCameraDetails(CameraDto camera) {
        return camera;
    }
}
