package com.laibao.reactor.test;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FluxWithIntervalTest {

    List<String> data = new ArrayList<String>(Arrays.asList("{A}","{B}","{C}","{D}","{E}","{F}","{G}"));

    private static Flux<Long> intervalFlux = Flux.interval(Duration.ofMillis(500));

    @Test
    public void testMap() throws InterruptedException {
        Flux<String> stringFlux =  intervalFlux.map(tick -> {
                                            if (tick < data.size()) {
                                                return "item " + tick + ": " + data.get(tick.intValue());
                                            }else {
                                                return "Done (tick == data.size())";
                                            }
                                    });

        stringFlux.take(data.size() + 1).subscribe(System.out::println);
        Thread.sleep(3000);
    }

    @Test
    public void testZipWith() throws InterruptedException {
        Flux<String> stringFlux = intervalFlux.zipWith(Flux.fromIterable(data), (i, item) -> "item " + i + ": " + item);
        stringFlux.subscribe(System.out::println);
        Thread.sleep(3000);
    }


    @Test
    public void testZipWithIterable() throws InterruptedException {

       Flux<String>  stringFlux = intervalFlux.zipWithIterable(data)
                                               .map(source -> "item " + source.getT1() + ": " + source.getT2());
        stringFlux.subscribe(System.out::println);
        Thread.sleep(3000);
    }
}
