package com.laibao.spring.reactor;


import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class WebClientMainTest {

    @Test
    public void readFibonacciNumbers() {
        WebClient client = WebClient.create("http://localhost:8080");
        Flux<Long> result = client.get()
                .uri("/fibonacci").accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Long.class);
        result.subscribe( x-> System.err.println(x));
    }

    @Test
    public void readFibonacciNumbersUsingExchange() {
        WebClient client = WebClient.create("http://localhost:8080");
        Flux<Long> result = client.get()
                .uri("/fibonacci").accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMapMany(response -> response.bodyToFlux(Long.class));

        result.subscribe( x-> System.err.println(x));
    }
}
