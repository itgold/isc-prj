package com.iscweb.common.service;

import org.springframework.security.access.prepost.PreAuthorize;

import static com.iscweb.common.security.ApplicationSecurity.ADMINISTRATORS_PERMISSION;

/**
 * An interface for application services which have been secured and are accessible
 * exclusively by users with the Administrator role by default.
 *
 * This should be the first interface you consider implementing when creating a new Service.
 */
@PreAuthorize(ADMINISTRATORS_PERMISSION)
public interface IApplicationSecuredService extends IApplicationService {
}
