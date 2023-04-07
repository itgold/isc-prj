package com.iscweb.common.service.integration;

/**
 * Contract for all User sync services globally scheduled to sync with third party systems.
 */
public interface IUserSyncService extends ISyncService {

    /**
     * @see ISyncService#friendlyName()
     */
    @Override
    default String friendlyName() {
        return "USER_SYNC_SERVICE";
    }
}
