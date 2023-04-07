package com.iscweb.common.model.entity;

import com.iscweb.common.model.IApplicationEntity;

/**
 * Device state contract.
 */
public interface IDeviceStateEntity extends IApplicationEntity {

    void setType(String type);

    String getType();

    void setValue(String value);

    String getValue();
}
