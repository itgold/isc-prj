package com.iscweb.common.service.integration;

import com.iscweb.common.service.IApplicationService;
import com.iscweb.common.sis.exceptions.SisConnectionException;

/**
 * The basic contract for all scheduled sync services, usually used to sync with third party systems.
 */
public interface ISyncService extends IApplicationService {

    /**
     * Used for logging/tracing purposes.
     *
     * @return Service friendly name.
     */
    String friendlyName();

    /**
     * Used to trigger processing.
     */
    void process(String syncId, String batchId) throws SisConnectionException;
}
