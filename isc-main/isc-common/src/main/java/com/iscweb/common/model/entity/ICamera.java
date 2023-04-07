package com.iscweb.common.model.entity;

import com.iscweb.common.model.ISchoolEntity;
import com.iscweb.common.model.metadata.CameraStatus;
import com.iscweb.common.model.metadata.CameraType;

import java.util.Set;

/**
 * Camera contract.
 *
 * @author skurenkov
 * Date: 4/28/19
 */
public interface ICamera extends ISchoolEntity {

    String getExternalId();

    CameraStatus getStatus();

    CameraType getType();

    String getName();
    String getDescription();

    Set<? extends IDeviceStateItem> getState();
}
