package com.laibao.spring.webflux.freemarker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FibonacciConfiguration {

    @Bean
    public RouterFunction<ServerResponse> fibonacciEndpoint() {

        Flux<Long> fibonacciGenerator = Flux.generate(() -> Tuples.<Long, Long>of(0L, 1L), (state, sink) -> {
                                                                    if (state.getT1() < 0) {
                                                                        sink.complete();
                                                                    } else {
                                                                        sink.next(state.getT1());
                                                                    }
                                                                    return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });

        Map<String, Flux> model = new HashMap<>();
        model.put("series",fibonacciGenerator.take(10));

        RouterFunction<ServerResponse> fibonacciRoute = RouterFunctions.route(RequestPredicates.path("/fibonacci"), request -> ServerResponse.ok().render("numbers",model));

        return fibonacciRoute;
    }
}
