package com.iscweb.common.model.entity;

import com.iscweb.common.model.IApplicationEntity;
import com.iscweb.common.model.metadata.IndexStatus;

/**
 * Index contract.
 */
public interface IIndex extends IApplicationEntity {

    String getGuid();

    String getName();

    String getDescription();

    IndexStatus getStatus();

}
