package com.iscweb.common.service.integration.door;

import com.iscweb.common.service.integration.ISyncService;

/**
 * Contract for all Door sync services globally scheduled to sync with third party systems.
 */
public interface IDoorSyncService extends ISyncService {

    /**
     * @see ISyncService#friendlyName()
     */
    @Override
    default String friendlyName() {
        return "DOOR_SYNC_SERVICE";
    }
}
