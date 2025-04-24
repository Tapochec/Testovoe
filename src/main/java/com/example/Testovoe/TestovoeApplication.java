package com.example.Testovoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TestovoeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestovoeApplication.class, args);
	}

}
