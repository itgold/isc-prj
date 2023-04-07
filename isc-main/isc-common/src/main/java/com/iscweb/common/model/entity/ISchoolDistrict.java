package com.iscweb.common.model.entity;

import com.iscweb.common.model.IApplicationEntity;
import com.iscweb.common.model.metadata.SchoolDistrictStatus;

/**
 * School District contract.
 */
public interface ISchoolDistrict extends IApplicationEntity {

    String getGuid();

    String getName();

    String getContactEmail();

    String getAddress();

    String getCity();

    String getState();

    String getZipCode();

    String getCountry();

    IRegion getRegion();

    SchoolDistrictStatus getStatus();
}
