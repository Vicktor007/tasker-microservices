package com.vic.tasker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TaskerTaskMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskerTaskMicroserviceApplication.class, args);
	}

}
