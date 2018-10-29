package com.laibao.reactor;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FluxWithIntervalMainApp {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== from Collection using map() ===");
        List<String> data = new ArrayList(Arrays.asList("{A}", "{B}", "{C}"));

        Flux<String> intervalFlux1 = Flux
                .interval(Duration.ofMillis(500))
                .map(tick -> {
                    if (tick < data.size()) {
                        return "item " + tick + ": " + data.get(tick.intValue());
                    }else {
                        return "Done (tick == data.size())";
                    }
                });

        intervalFlux1.take(data.size() + 1).subscribe(System.out::println);
        Thread.sleep(3000);

        System.out.println("=== from Collection using zipWithIterable() and map() ===");
        Flux<String> intervalFlux2 = Flux
                .interval(Duration.ofMillis(500))
                .zipWithIterable(data)
                .map(source -> "item " + source.getT1() + ": " + source.getT2());

        intervalFlux2.subscribe(System.out::println);
        Thread.sleep(3000);

        System.out.println("=== from Flux using zipWith() ===");
        Flux<String> flux = Flux.just("{A}", "{B}", "{C}");
        Flux<String> intervalFlux3 = Flux
                .interval(Duration.ofMillis(500))
                .zipWith(flux, (i, item) -> "item " + i + ": " + item);

        intervalFlux3.subscribe(System.out::println);
        Thread.sleep(3000);
    }
}
