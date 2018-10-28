package com.laibao.spring.reactor.functionalway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunctions;

@SpringBootApplication
public class SpringReactorFunctionalWayApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringReactorFunctionalWayApplication.class, args);
	}
}
