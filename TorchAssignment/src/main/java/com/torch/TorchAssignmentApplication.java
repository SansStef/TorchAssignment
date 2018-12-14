package com.torch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TorchAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(TorchAssignmentApplication.class, args);
	}

}

