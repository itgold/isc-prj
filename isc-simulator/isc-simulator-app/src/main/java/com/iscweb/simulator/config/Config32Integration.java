package com.iscweb.simulator.config;

import com.iscweb.common.service.integration.camera.ICameraService;
import com.iscweb.common.service.integration.door.IDoorActionsService;
import com.iscweb.service.integration.DefaultCameraService;
import com.iscweb.service.integration.DefaultDoorActionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Slf4j
@Order(32)
@Configuration
@ComponentScan(basePackages = {"com.iscweb.integration.config"})
public class Config32Integration {

    @Bean
    @ConditionalOnMissingBean
    ICameraService defaultCameraService() {
        return new DefaultCameraService();
    }

    @Bean
    @ConditionalOnMissingBean
    IDoorActionsService defaultDoorService() {
        return new DefaultDoorActionsService();
    }
}
