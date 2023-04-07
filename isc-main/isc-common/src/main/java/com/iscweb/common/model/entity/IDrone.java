package com.iscweb.common.model.entity;

import com.iscweb.common.model.ISchoolEntity;
import com.iscweb.common.model.metadata.DroneStatus;
import com.iscweb.common.model.metadata.DroneType;
import org.locationtech.jts.geom.Point;

import java.util.Set;

/**
 * Drone contract.
 *
 * @author skurenkov
 * Date: 4/28/19
 */
public interface IDrone extends ISchoolEntity {

    String getExternalId();

    DroneStatus getStatus();

    DroneType getType();

    Point getCurrentLocation();

    String getName();

    String getDescription();

    Set<? extends IDeviceStateItem> getState();
}
