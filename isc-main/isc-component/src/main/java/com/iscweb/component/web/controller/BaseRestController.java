package com.iscweb.component.web.controller;

import com.iscweb.common.service.IApplicationService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The base Controller for all application REST controllers, which provides access to business logic
 * through the Service layer. Most REST controllers must extend this class and provide a default
 * implementation of IApplicationService that it used as a business logic service.
 *
 * @param <S> Service associated with the Controller
 */
public abstract class BaseRestController<S extends IApplicationService> implements IApplicationController {

    /**
     * Default service implementation for business logic delegation.
     */
    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private S service;
}
