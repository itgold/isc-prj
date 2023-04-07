package com.iscweb.common.model.entity;

import com.iscweb.common.model.ISchoolEntity;
import com.iscweb.common.model.metadata.RegionStatus;
import com.iscweb.common.model.metadata.RegionType;
import org.locationtech.jts.geom.Polygon;

/**
 * Region contract.
 *
 * @author skurenkov
 * Date: 4/28/19
 */
public interface IRegion extends ISchoolEntity {

    String getName();

    RegionType getType();

    Polygon getGeoBoundaries();

    Float getGeoZoom();

    Float getGeoRotation();

    RegionStatus getStatus();
}
