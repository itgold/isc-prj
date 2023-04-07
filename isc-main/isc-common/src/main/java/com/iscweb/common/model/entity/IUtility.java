package com.iscweb.common.model.entity;

import com.iscweb.common.model.ISchoolEntity;
import com.iscweb.common.model.metadata.UtilityStatus;
import com.iscweb.common.model.metadata.UtilityType;

/**
 * Utility contract.
 *
 * @author arezen
 * Date: 01/14/2022
 */
public interface IUtility extends ISchoolEntity {
    String getExternalId();

    String getName();

    String getDescription();

    UtilityStatus getStatus();

    UtilityType getType();
}
