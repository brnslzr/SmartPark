package com.SmartPark.smartpark_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartparkApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartparkApiApplication.class, args);
	}

}
