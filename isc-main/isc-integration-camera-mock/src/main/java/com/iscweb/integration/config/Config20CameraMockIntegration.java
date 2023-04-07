package com.iscweb.integration.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

@Slf4j
@Order(20)
@Configuration
@Profile("camera-mock")
@ComponentScan(basePackages = {"com.iscweb.integration.cameras.mock.services"})
public class Config20CameraMockIntegration {
}
