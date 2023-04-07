package com.iscweb.common.model.entity;

import com.iscweb.common.model.IApplicationEntity;
import com.iscweb.common.model.metadata.UserStatus;

import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * A User that logs into the system. Users belong to {@link ISchoolDistrict} entities,
 * and own certain roles.
 */
public interface IUser extends IApplicationEntity, Principal {

    String getGuid();

    ISchoolDistrict getSchoolDistrict();

    String getEmail();

    String getPassword();

    String getFirstName();

    String getLastName();

    String getImageUrl();

    Set<? extends IRole> getRoles();

    ZonedDateTime getLastLogin();

    ZonedDateTime getActivationDate();

    UserStatus getStatus();

    ZonedDateTime getStatusDate();

}
