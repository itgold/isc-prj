package com.iscweb.service.api;

import com.iscweb.common.service.IApplicationSecuredService;
import org.springframework.security.access.prepost.PreAuthorize;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

/**
 * Base class for public API services.
 */
public abstract class BasePublicApiService implements IApplicationSecuredService {

    @PreAuthorize(IS_AUTHENTICATED)
    public abstract String getVersion();

}
