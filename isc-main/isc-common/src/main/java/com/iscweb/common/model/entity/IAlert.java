package com.iscweb.common.model.entity;

import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IApplicationEntity;
import com.iscweb.common.model.metadata.AlertSeverity;
import com.iscweb.common.model.metadata.AlertStatus;

/**
 * Alert contract.
 *
 * @author skurenkov
 * Date: 4/28/19
 */
public interface IAlert extends IApplicationEntity {

    String getTriggerId();

    String getDeviceId();

    EntityType getDeviceType();

    AlertSeverity getSeverity();

    AlertStatus getStatus();

    int getCount();

    String getEventId();

    String getSchoolId();

    String getDistrictId();

    String getCode();

    String getDescription();
}
