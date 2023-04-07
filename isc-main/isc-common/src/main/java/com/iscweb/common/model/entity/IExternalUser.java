package com.iscweb.common.model.entity;

import com.iscweb.common.model.IApplicationEntity;
import com.iscweb.common.model.metadata.UserStatus;

/**
 * A User that logs into the system. Users belong to {@link ISchoolDistrict} entities,
 * and own certain roles.
 */
public interface IExternalUser extends IApplicationEntity {

    String getExternalId();

    String getSource();

    String getTitle();

    String getFirstName();

    String getLastName();

    String getPhoneNumber();

    UserStatus getStatus();

    String getSchoolSite();

    String getOfficialJobTitle();

    String getIdFullName();

    String getIdNumber();

    String getOfficeClass();
}
