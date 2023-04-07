package com.iscweb.common.model.entity;

import com.iscweb.common.model.IApplicationEntity;
import com.iscweb.common.model.metadata.IntegrationStatus;

/**
 * Integration contract.
 */
public interface IIntegration extends IApplicationEntity {

    String getGuid();

    String getName();

    String getDescription();

    String getConnectionParams();

    String getMetaParams();

    IntegrationStatus getStatus();

}
