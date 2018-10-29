package com.laibao.reactor.test;

import org.junit.Test;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class MonoTest {

    @Test
    public void testSimpleMono() {
        /**
         * 1: Empty Mono
         */
        Mono<String> emptyMon = Mono.empty();
        emptyMon.subscribe(System.out::println);


        /**
         * Mono with String value
         */
        Mono<String> stringMono = Mono.just("金戈");
        stringMono.subscribe(System.out::println);

        /**
         * Mono that emits an Exception
         */
        Mono<Throwable> throwableMono = Mono.error(new RuntimeException("出错了啊"));
        throwableMono.subscribe(System.out::println);
    }

    @Test
    public void testJustMethod() {
        Mono<String> stringMono = Mono.just("Red");
        Mono<Void> voidMono = Mono.justOrEmpty(null);
        Mono<Void> voidMono1 = Mono.justOrEmpty(Optional.empty());
    }

    @Test
    public void testFromMethod() {
        Mono.fromSupplier(() -> 1);

        Mono.fromCallable(() -> new String[]{"color"}).subscribe(t -> System.out.println("received " + t));

        Mono.fromRunnable(() -> System.out.println(" ")).subscribe(t -> System.out.println());
    }
}
