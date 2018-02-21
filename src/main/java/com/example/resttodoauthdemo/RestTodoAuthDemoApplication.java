package com.example.resttodoauthdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableSocial
@EnableJpaRepositories
@SpringBootApplication
public class RestTodoAuthDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestTodoAuthDemoApplication.class, args);
	}
}
