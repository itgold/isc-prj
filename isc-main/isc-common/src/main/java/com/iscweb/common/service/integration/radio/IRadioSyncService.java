package com.iscweb.common.service.integration.radio;

import com.iscweb.common.service.integration.ISyncService;

/**
 * Contract for all Radio sync services globally scheduled to sync with third party systems.
 */
public interface IRadioSyncService extends ISyncService {

    /**
     * @see ISyncService#friendlyName()
     */
    @Override
    default String friendlyName() {
        return "RADIO_SYNC_SERVICE";
    }
}
