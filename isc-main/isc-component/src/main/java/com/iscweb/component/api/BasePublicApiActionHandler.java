package com.iscweb.component.api;

import com.iscweb.common.service.IApplicationSecuredService;
import com.iscweb.service.api.BasePublicApiService;
import org.springframework.security.access.prepost.PreAuthorize;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

/**
 * A service that provides base public API functionality. Every public API version should have its own service
 * implementation that resides in a corresponding service package (ex. <code>com.iscweb.component.api.v11</code>.)
 *
 * @author skurenkov
 */
public abstract class BasePublicApiActionHandler implements IApplicationSecuredService {

    /**
     * Each implementation should have an associated {@link BasePublicApiService}.
     */
    protected abstract BasePublicApiService getService();

    @PreAuthorize(IS_AUTHENTICATED)
    public String getVersion() {
        return getService().getVersion();
    }

}
