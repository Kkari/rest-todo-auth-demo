package com.example.resttodoauthdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class RestTodoAuthDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestTodoAuthDemoApplication.class, args);
	}
}
