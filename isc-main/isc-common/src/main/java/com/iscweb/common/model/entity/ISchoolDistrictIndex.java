package com.iscweb.common.model.entity;

import com.iscweb.common.model.IApplicationEntity;

/**
 * A contract for an object which associates an Index to a School District.
 */
public interface ISchoolDistrictIndex extends IApplicationEntity {

    IIndex getIndex();

    ISchoolDistrict getSchoolDistrict();

}
