package com.laibao.reactor.test;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class FluxCombinePublisherTest {

    private Mono<String> mono1 = Mono.just("grokonez.com");

    private Mono<String> mono2 = Mono.just("|Java Technology");

    private Mono<String> mono3 = Mono.just("|Spring Framework");

    private Flux<String> flux1 = Flux.just("{1}", "{2}", "{3}", "{4}");

    private Flux<String> flux2 = Flux.just("|A|", "|B|", "|C|");

    @Test
    public void testInitializePublisher() throws InterruptedException {
        /**
         * FLux emits item each 500ms
         */
        Flux<String> intervalFlux1 = Flux
                .interval(Duration.ofMillis(500))
                .zipWith(flux1, (i, string) -> string);

        intervalFlux1.subscribe(System.out::println);

        /**
         * FLux emits item each 700ms
         */
        Flux<String> intervalFlux2 = Flux
                .interval(Duration.ofMillis(700))
                .zipWith(Flux.from(flux2), (i, string) -> string);

        intervalFlux2.subscribe(System.out::println);
        Thread.sleep(3000);
    }

    @Test
    public void testConcat() throws InterruptedException {

        Flux<String> intervalFlux1 = Flux
                .interval(Duration.ofMillis(500))
                .zipWith(flux1, (i, string) -> string);

        Flux<String> intervalFlux2 = Flux
                .interval(Duration.ofMillis(700))
                .zipWith(Flux.from(flux2), (i, string) -> string);

        Flux.concat(mono1, mono3, mono2).subscribe(System.out::println);
        System.out.println();

        Flux.concat(flux2, flux1).subscribe(System.out::println);
        System.out.println();

        Flux.concat(intervalFlux2, flux1).subscribe(System.out::println);
        System.out.println();

        Flux.concat(intervalFlux2, intervalFlux1).subscribe(System.out::println);
        System.out.println();

        Thread.sleep(5000);
    }

    @Test
    public void testConcatWith() throws InterruptedException {
        Flux<String> intervalFlux1 = Flux
                .interval(Duration.ofMillis(500))
                .zipWith(flux1, (i, string) -> string);

        Flux<String> intervalFlux2 = Flux
                .interval(Duration.ofMillis(700))
                .zipWith(Flux.from(flux2), (i, string) -> string);

        mono1.concatWith(mono2).concatWith(mono3).subscribe(System.out::println);
        // grokonez.com|Java Technology|Spring Framework
        System.out.println();

        flux1.concatWith(flux2).subscribe(System.out::println);
        // {1}{2}{3}{4}|A||B||C|
        System.out.println();

        intervalFlux1.concatWith(flux2).subscribe(System.out::println);
        // {1}{2}{3}{4}|A||B||C|
        // each of {1},{2},{3},{4} emits each 700ms, then |A|,|B|,|C| emit immediately
        Thread.sleep(3000);
        System.out.println();

        intervalFlux1.concatWith(intervalFlux2).subscribe(System.out::println);
        // {1}{2}{3}{4}|A||B||C|
        // each of {1},{2},{3},{4} emits each 500ms, then each of |A|,|B|,|C| emits each 700ms
        Thread.sleep(5000);
    }

    @Test
    public void testZip() {
        Flux.zip(flux2, flux1, (itemFlux2, itemFlux1) -> "-[" + itemFlux2 + itemFlux1 + "]-")
             .subscribe(System.out::println);
        // -[|A|{1}]--[|B|{2}]--[|C|{3}]-
    }

    @Test
    public void testZipWith() {
        flux1.zipWith(flux2, (itemFlux1, itemFlux2) -> "-[" + itemFlux1 + itemFlux2 + "]-")
                .subscribe(System.out::println);
        // -[{1}|A|]--[{2}|B|]--[{3}|C|]-
    }

    @Test
    public void testMerge() throws InterruptedException {
        Flux<String> intervalFlux1 = Flux
                .interval(Duration.ofMillis(500))
                .zipWith(flux1, (i, string) -> string);

        Flux<String> intervalFlux2 = Flux
                .interval(Duration.ofMillis(700))
                .zipWith(Flux.from(flux2), (i, string) -> string);

        Flux.merge(intervalFlux1, intervalFlux2).subscribe(System.out::println);
        // {1}|A|{2}|B|{3}{4}|C|
        Thread.sleep(3000);
    }

    @Test
    public void testMergeWith() throws InterruptedException {
        Flux<String> intervalFlux1 = Flux
                .interval(Duration.ofMillis(500))
                .zipWith(flux1, (i, string) -> string);

        Flux<String> intervalFlux2 = Flux
                .interval(Duration.ofMillis(700))
                .zipWith(Flux.from(flux2), (i, string) -> string);
        intervalFlux1.mergeWith(intervalFlux2).subscribe(System.out::println);
        Thread.sleep(3000);
        // {1}|A|{2}|B|{3}{4}|C|
    }
}
