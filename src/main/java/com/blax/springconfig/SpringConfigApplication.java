package com.blax.springconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringConfigApplication.class, args);
	}

}
