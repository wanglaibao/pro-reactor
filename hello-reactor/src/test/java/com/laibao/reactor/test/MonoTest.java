package com.laibao.reactor.test;

import org.junit.Test;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class MonoTest {

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
