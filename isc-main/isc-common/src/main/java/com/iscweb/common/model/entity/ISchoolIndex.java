package com.iscweb.common.model.entity;

import com.iscweb.common.model.IApplicationEntity;

/**
 * A contract for an object which associates an Index with a School.
 */
public interface ISchoolIndex extends IApplicationEntity {

    ISchool getSchool();

    IIndex getIndex();

}
