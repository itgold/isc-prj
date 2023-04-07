package com.iscweb.integration.cameras.mock.services.streaming;

import com.iscweb.integration.cameras.mock.services.streaming.events.MipCameraEventDto;

public interface CameraEventListener<T extends MipCameraEventDto> {
    void onEvent(String cameraId, T event);
}
