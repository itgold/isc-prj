package com.iscweb.component.web.controller.rest;

import com.iscweb.component.web.controller.BaseInternalApiController;
import com.iscweb.service.SchoolDistrictService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import static com.iscweb.common.security.ApplicationSecurity.ADMINISTRATORS_PERMISSION;

@Slf4j
@RestController
@Api("REST controller for managing School District entities")
@PreAuthorize(ADMINISTRATORS_PERMISSION)
public class SchoolDistrictController extends BaseInternalApiController<SchoolDistrictService> {

    /**
     * @see com.iscweb.common.model.IApplicationComponent#getLogger()
     */
    @Override
    public Logger getLogger() {
        return log;
    }
}
