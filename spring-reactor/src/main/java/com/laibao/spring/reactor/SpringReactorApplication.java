package com.laibao.spring.reactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class SpringReactorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactorApplication.class, args);
	}
}
