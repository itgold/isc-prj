package com.iscweb.app.main.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iscweb.common.util.ObjectMapperUtility;
import com.iscweb.persistence.repositories.impl.RegionJpaRepository;
import com.iscweb.persistence.repositories.impl.RoleJpaRepository;
import com.iscweb.persistence.repositories.impl.SchoolDistrictJpaRepository;
import com.iscweb.service.converter.Convert;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Initializes the rest of the application.
 * This configuration is getting invoked last in the configurations invocation chain.
 */
@Slf4j
@Order(33)
@Component
public class Config33ApplicationInitializer {

    @Getter
    @Setter(onMethod = @__({@Autowired, @Qualifier("defaultObjectMapper")}))
    private ObjectMapper objectMapper;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RoleJpaRepository roleRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RegionJpaRepository regionRepository;

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private SchoolDistrictJpaRepository schoolDistrictRepository;

    @PostConstruct
    public void constructed() {
        Convert.init(getRoleRepository(),
                     getRegionRepository(),
                     getSchoolDistrictRepository());

        ObjectMapperUtility.init(getObjectMapper());

        //used to propagate security context to the asynchronous tasks that we run.
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

        log.info("█████████████████████████████ Application Initialized ████████████████████████████████");
    }
}
