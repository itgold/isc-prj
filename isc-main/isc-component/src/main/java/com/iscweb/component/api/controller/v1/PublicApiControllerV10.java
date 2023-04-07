package com.iscweb.component.api.controller.v1;

import com.iscweb.component.api.BasePublicApiController;
import com.iscweb.component.api.handler.v1.IPublicApiContractV10;
import com.iscweb.component.api.handler.v1.PublicApiActionHandlerV10;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Public API controller that handles requests to the public REST API v1.0.
 *
 * @author skurenkov
 */
@Slf4j
@RestController
@RequestMapping(path = {"/api/v1", "/api/1.0"})
@Api("Api Version 1.0")
public class PublicApiControllerV10 extends BasePublicApiController<PublicApiActionHandlerV10> implements IPublicApiContractV10 {

    @Override
    @Autowired
    @Qualifier("publicApiActionHandlerV10")
    public void setService(PublicApiActionHandlerV10 service) {
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
