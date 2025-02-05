package com.vic.tasker_discovery_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TaskerDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskerDiscoveryServerApplication.class, args);
	}

}
