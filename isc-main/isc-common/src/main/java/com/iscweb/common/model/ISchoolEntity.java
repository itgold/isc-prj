package com.iscweb.common.model;

import com.iscweb.common.model.entity.IRegion;
import org.locationtech.jts.geom.Point;

import java.util.Set;

/**
 * An interface for entities which are located within a school.
 */
public interface ISchoolEntity extends IApplicationEntity {

    Set<? extends IRegion> getRegions();

    Point getGeoLocation();
}
