package com.iscweb.common.model.entity;

import com.iscweb.common.model.IApplicationEntity;

/**
 * A user role. Roles are associated with permissions about operations that can be performed in the system.
 * They mimic the roles that Spring as a framework uses to secure calls.
 */
public interface IRole extends IApplicationEntity {

    String getName();

}
