package com.laibao.spring.reactor.functionalway.configuration;

import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.nio.charset.Charset;
import java.util.Optional;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;


@Configuration
public class SpringReactorFunctionalConfiguration {

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


    @Bean
    RouterFunction<ServerResponse> functionalEndpoint() {
        HandlerFunction<ServerResponse> helloWorld = (ServerRequest request) -> {
            Optional<String> name = request.queryParam("name");
            Publisher data = Flux.just("Welcome to ", name.orElse("the world."));
            return ServerResponse.ok().body(fromPublisher(data, String.class));
        };

        RouterFunction<ServerResponse> helloWorldRoute = RouterFunctions.route(RequestPredicates.path("/welcome"), helloWorld)
                                                                          .filter(((request, next) -> {
                                                                              if (request.headers().acceptCharset().contains(Charset.forName("UTF-8"))) {
                                                                                  return next.handle(request);
                                                                              } else {
                                                                                  return ServerResponse.status(HttpStatus.BAD_REQUEST).build();
                                                                              }
                                                                          }));
        return helloWorldRoute;

    }

    @Bean
    RouterFunction<ServerResponse> fibonacciEndpoint() {

        Flux<Long> fibonacciGenerator = Flux.generate(() -> Tuples.<Long, Long>of(0L, 1L), (state, sink) -> {
                                                                    if (state.getT1() < 0) {
                                                                        sink.complete();
                                                                    } else {
                                                                        sink.next(state.getT1());
                                                                    }
                                                                    return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });

        RouterFunction<ServerResponse> fibonacciRoute =
                RouterFunctions.route(RequestPredicates.path("/fibonacci"), request -> ServerResponse.ok().body(fromPublisher(fibonacciGenerator.take(20), Long.class)));
        return fibonacciRoute;
    }
}
