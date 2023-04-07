package com.iscweb.integration.cameras.mip.services.streaming;

import com.iscweb.integration.cameras.mip.services.streaming.events.MipCameraEventDto;

public interface CameraEventListener<T extends MipCameraEventDto> {
    void onEvent(String cameraId, String streamId, T event);
}
