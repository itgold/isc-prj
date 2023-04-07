package com.iscweb.common.model.entity;

import com.iscweb.common.model.ISchoolEntity;
import com.iscweb.common.model.metadata.DoorBatteryStatus;
import com.iscweb.common.model.metadata.DoorConnectionStatus;
import com.iscweb.common.model.metadata.DoorOnlineStatus;
import com.iscweb.common.model.metadata.DoorOpeningMode;
import com.iscweb.common.model.metadata.DoorStatus;
import com.iscweb.common.model.metadata.DoorTamperStatus;
import com.iscweb.common.model.metadata.DoorType;

import java.util.Set;

/**
 * Door contract.
 *
 * @author skurenkov
 * Date: 4/28/19
 */
public interface IDoor extends ISchoolEntity {

    String getExternalId();

    String getName();

    String getDescription();

    DoorStatus getStatus();

    DoorType getType();

    DoorConnectionStatus getConnectionStatus();

    DoorOnlineStatus getOnlineStatus();

    DoorBatteryStatus getBatteryStatus();

    DoorTamperStatus getTamperStatus();

    DoorOpeningMode getOpeningMode();

    Integer getBatteryLevel();

    Boolean isUpdateRequired();

    Set<? extends IDeviceStateItem> getState();
}
