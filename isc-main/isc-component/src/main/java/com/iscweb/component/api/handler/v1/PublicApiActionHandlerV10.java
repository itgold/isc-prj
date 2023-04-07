package com.iscweb.component.api.handler.v1;

import com.iscweb.component.api.BasePublicApiActionHandler;
import com.iscweb.service.api.v1.v10.PublicApiServiceV10;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import static com.iscweb.common.security.ApplicationSecurity.IS_AUTHENTICATED;

/**
 * A service implementation for public REST API v1.0.
 *
 * @author skurenkov
 */
@Slf4j
@Service
@PreAuthorize(IS_AUTHENTICATED)
public class PublicApiActionHandlerV10 extends BasePublicApiActionHandler implements IPublicApiContractV10 {

    private PublicApiServiceV10 publicApiServiceV10;

    @Override
    public PublicApiServiceV10 getService() {
        return publicApiServiceV10;
    }

    @Autowired
    @Qualifier("publicApiServiceV10")
    public void setPublicApiServiceV10(PublicApiServiceV10 publicApiServiceV10) {
        this.publicApiServiceV10 = publicApiServiceV10;
    }
}
