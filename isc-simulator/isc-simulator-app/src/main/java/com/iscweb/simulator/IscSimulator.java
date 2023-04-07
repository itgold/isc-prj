package com.iscweb.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.iscweb.simulator.config"})
public class IscSimulator {

	public static void main(String[] args) {
		SpringApplication.run(IscSimulator.class, args);
	}

}
