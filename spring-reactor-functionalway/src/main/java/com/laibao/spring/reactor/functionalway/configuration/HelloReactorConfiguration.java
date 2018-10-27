package com.laibao.spring.reactor.functionalway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import java.util.Optional;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;


@Configuration
public class HelloReactorConfiguration {

    @Bean
    public HandlerFunction<ServerResponse> helloHandler() {
        HandlerFunction<ServerResponse> helloHandler = (ServerRequest request) -> {
            Optional<String> name = request.queryParam("name");
            return ServerResponse.ok().body(fromObject("Hello to " + name.orElse("the world.")));
        };
        return helloHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> getHelloRouter() {
        RouterFunction<ServerResponse> router = RouterFunctions.route(RequestPredicates.path("/hello"),helloHandler());
        return router;
    }
}
