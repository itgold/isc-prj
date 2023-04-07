package com.iscweb.common.model.entity;

import java.time.ZonedDateTime;

/**
 * Device state item contract.
 *
 * @author dmorozov
 * Date: 4/28/19
 */
public interface IDeviceStateItem {

    String getName();

    String getValue();

    ZonedDateTime getUpdated();
}
