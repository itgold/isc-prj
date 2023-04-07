package com.iscweb.common.model.entity;

import com.iscweb.common.model.IApplicationEntity;
import com.iscweb.common.model.metadata.SchoolStatus;

/**
 * School contract.
 */
public interface ISchool extends IApplicationEntity {

    String getGuid();

    String getName();

    String getContactEmail();

    String getAddress();

    String getCity();

    String getState();

    String getZipCode();

    String getCountry();

    SchoolStatus getStatus();

    IRegion getRegion();

    ISchoolDistrict getSchoolDistrict();
}
