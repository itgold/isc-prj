package com.iscweb.component.api.controller.v1;

import com.iscweb.component.api.BasePublicApiController;
import com.iscweb.component.api.handler.v1.IPublicApiContractV11;
import com.iscweb.component.api.handler.v1.PublicApiActionHandlerV11;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Public API controller that handles requests to the public REST API v1.1.
 *
 * @author skurenkov
 */
@Slf4j
@RestController
@RequestMapping(path = {"/api/1.1"})
@Api("Api Version 1.1")
public class PublicApiControllerV11 extends BasePublicApiController<PublicApiActionHandlerV11> implements IPublicApiContractV11 {

    // V10 ENDPOINTS

    // NEW ENDPOINTS

    @Override
    @Autowired
    @Qualifier("publicApiActionHandlerV11")
    public void setService(PublicApiActionHandlerV11 service) {
        super.setService(service);
    }

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
