package com.iscweb.component.api.handler.v1;

import com.iscweb.service.api.v1.v11.PublicApiServiceV11;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

/**
 * Service implementation for public REST API v 1.1.
 */
@Service
@PreAuthorize(IS_AUTHENTICATED)
public class PublicApiActionHandlerV11 extends PublicApiActionHandlerV10 implements IPublicApiContractV11 {

    private PublicApiServiceV11 publicApiServiceV11;


    @Override
    public PublicApiServiceV11 getService() {
        return publicApiServiceV11;
    }

    @Autowired
    @Qualifier("publicApiServiceV11")
    public void setService(PublicApiServiceV11 publicApiServiceV11) {
        this.publicApiServiceV11 = publicApiServiceV11;
    }
}
