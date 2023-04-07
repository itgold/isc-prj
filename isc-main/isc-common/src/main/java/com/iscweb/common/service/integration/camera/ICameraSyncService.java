package com.iscweb.common.service.integration.camera;

import com.iscweb.common.service.integration.ISyncService;

/**
 * Contract for all Camera sync services globally scheduled to sync with third party systems.
 */
public interface ICameraSyncService extends ISyncService {

    /**
     * @see ISyncService#friendlyName()
     */
    @Override
    default String friendlyName() {
        return "CAMERA_SYNC_SERVICE";
    }
}
