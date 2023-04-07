package com.iscweb.common.model.entity;

import com.iscweb.common.model.ISchoolEntity;
import com.iscweb.common.model.metadata.SafetyStatus;
import com.iscweb.common.model.metadata.SafetyType;

/**
 * Safety contract.
 *
 * @author arezen
 * Date: 01/14/2022
 */
public interface ISafety extends ISchoolEntity {
    String getExternalId();

    String getName();

    String getDescription();

    SafetyStatus getStatus();

    SafetyType getType();
}
