package com.iscweb.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * Main entry point!
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.iscweb.app.main.config"})
public class ControlCenter {

    /**
     * We want to unify timezone used by our server disregards on the server timezone where it is deployed.
     */
    @PostConstruct
    public void initialize() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    /**
     * ISC main application entry point.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(ControlCenter.class, args);
    }
}
