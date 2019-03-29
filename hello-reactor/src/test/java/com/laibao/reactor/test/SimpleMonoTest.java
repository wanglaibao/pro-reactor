package com.laibao.reactor.test;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SimpleMonoTest {

    @Test
    public void testJust() {
        Mono<String> stringMono = Mono.just("金戈");
        stringMono.subscribe(System.out::println);

        Mono<String> stringMono1 = Mono.justOrEmpty(Optional.of("wangwangjiao"));
        stringMono1.subscribe(System.out::println);

        Mono<String> stringMono2 = Mono.justOrEmpty(Optional.empty());
        stringMono2.subscribe(System.out::println);
    }

    @Test
    public void testFrom() {
        Mono<Integer> integerMono = Mono.fromSupplier(() -> 1);
        integerMono.subscribe(System.out::println);

        Mono<String[]> fromCallable  = Mono.fromCallable(() -> new String[]{"color"});

        fromCallable.subscribe(strings -> {
            for(String element:strings) {
                System.err.println(element);
            }
        });

        Mono<Void> voidMond = Mono.fromRunnable(() -> System.out.println(" "));
        voidMond.subscribe(System.out::println);

        Mono<String> fromFuture  = Mono.fromFuture(new CompletableFuture<String>(){
            @Override
            public String get() throws InterruptedException, ExecutionException {
                return "金戈来了啊";
            }
        });


        Mono.from(Flux.just("Red", "Blue", "Yellow", "Black")).subscribe(t -> System.err.println(t));
    }
}
