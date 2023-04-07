package com.iscweb.app.main.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;

/**
 * This is the configuration class for Time Machine module.
 * <p>
 * Time Machine is a set of scheduled services used for doing background
 * scheduled maintenance jobs.
 * <p>
 * When time prole is enabled this configuration will load Time Machine classes
 * and services to enable this application module.
 */
@Slf4j
@Order(5)
@Configuration
@Profile("time")
@ComponentScan(basePackages = {"com.iscweb.component.time"})
public class Config05TimeMachineComponent {

    /**
     * Log message with after successful configuration initialization.
     */
    @PostConstruct
    public void constructed() {
        log.info("█████████████████████████████ Time Machine Component Enabled ████████████████████████████████");
    }

    @Bean("mainAsyncTaskScheduler")
    public TaskScheduler getTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(1);
        threadPoolTaskScheduler.setThreadNamePrefix("ISC_TASKS_SCHEDULE");
        return threadPoolTaskScheduler;
    }
}

