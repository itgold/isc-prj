package com.iscweb.component.web.controller;

import com.iscweb.common.service.IApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Base class for internal API Controllers.
 *
 * @param <S> Service associated with the Controller
 * @author skurenkov
 */
@Slf4j
@RequestMapping("/rest")
@CrossOrigin(origins = "${spring.profiles.dev-ui.url}", allowedHeaders = "*", allowCredentials = "true")
public abstract class BaseInternalApiController<S extends IApplicationService> extends BaseRestController<S> {

    /**
     * Initializes pageable property from the request parameters.
     *
     * @param pageNumber requested page number.
     * @param pageSize number of elements per page.
     * @param sort sorting parameters.
     * @param direction sorting direction.
     * @return spring pageable object.
     */
    protected Pageable initPageable(Integer pageNumber,
                                    Integer pageSize,
                                    String sort,
                                    Sort.Direction direction) {
        if (pageSize > 100) {
            pageSize = 100;
            log.debug("Defaulting pageSize parameter to 100");
        }

        return PageRequest.of(pageNumber, pageSize, Sort.by(direction, sort));
    }
}
