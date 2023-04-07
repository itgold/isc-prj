package com.iscweb.common.service.integration.camera;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.exception.UnableToConnectException;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.service.IApplicationService;

/**
 * A generic interface for services that provides cameras related functionality.
 */
public interface ICameraService extends IApplicationService {

    /**
     * Get camera server info.
     *
     * @return service info.
     */
    String serviceInfo();

    /**
     * Collect camera state details.
     *
     * @param camera camera dto for fetching the data.
     * @return updated camera dto object.
     */
    CameraDto getCameraDetails(CameraDto camera);

    /**
     * Start video streaming from a specific camera.
     *
     * @param cameraId camera identifier.
     * @param streamId stream identifier.
     * @return camera stream object implementation.
     * @throws UnableToConnectException if operation failed.
     */
    ICameraStream streamVideo(String cameraId, String streamId) throws ServiceException;

}
