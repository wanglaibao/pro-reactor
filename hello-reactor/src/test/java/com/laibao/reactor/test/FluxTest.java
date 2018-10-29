package com.laibao.reactor.test;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static reactor.core.publisher.Flux.*;

public class FluxTest {

    @Test
    public void testSimpleFlux() {

        /**
         * Empty Flux
         */
        Flux<String> emptyFlux = Flux.empty();
        emptyFlux.subscribe(System.out::println);
        System.out.println();
        /**
         *
         */
        Flux<String> stringFlux = Flux.just("金戈","金山","金玉","金毛");
        stringFlux.subscribe(System.out::println);
        System.out.println();
        /**
         * Flux from Collections
         */
        List<String> list = Arrays.asList("JAVA", "SAMPLE", "APPROACH", ".COM");
        Flux<String> listFlux  = Flux.fromIterable(list);
        listFlux.subscribe(System.out::println);
        System.out.println();
        /**
         *  Flux that is infinite and ticks every x units of time with an increasing Long
         */
        Flux<Long> counterFlux = Flux.interval(Duration.ofMillis(2));
        counterFlux.subscribe(System.out::println);
        /**
         * Flux that emits an Exception
         */
        Flux<Throwable> throwableFlux = Flux.error(new RuntimeException("出错了啊"));
        throwableFlux.subscribe(System.out::println);
    }


    @Test
    public void testJustMethod() {
        Flux<String> stringFlux = just("Red");
        System.out.println(stringFlux.blockFirst());
        System.out.println();

        Flux<String>  stringFlux1 = just("Red", "Blue", "Yellow", "Black");
        stringFlux1.toStream().forEach(System.out::println);
    }

    @Test
    public void testFromMethod() {
        Flux<Integer> integerFlux = fromArray(new Integer[]{1,1,2,3,5,8,13});
        integerFlux.toStream().forEach(System.out::println);
        System.out.println();

        Flux<String> stringFlux = fromIterable(Arrays.asList("Red", "Blue", "Yellow", "Black"));
        stringFlux.toStream().forEach(System.out::println);
        System.out.println();

        Flux<Integer> integerFlux1 = fromStream(IntStream.range(1,100).boxed());
        integerFlux1.toStream().forEach(System.out::println);
        System.out.println();
    }

}
