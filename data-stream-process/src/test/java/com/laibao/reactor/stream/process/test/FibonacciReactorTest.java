package com.laibao.reactor.stream.process.test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.Duration;

public class FibonacciReactorTest {

    private  Flux<Long> fibonacciGenerator = null;

    @Before
    public void init() {
        fibonacciGenerator = Flux.generate(() -> Tuples.<Long,Long>of(0L, 1L),
                (state, sink) -> {
                    if (state.getT1() < 0) {
                        sink.complete();
                    } else {
                        sink.next(state.getT1());
                    }
                    return Tuples.of(state.getT2(), state.getT1() + state.getT2());
                });
    }

    @Test
    public void testFilterFibonacciNumber() {
        fibonacciGenerator.filter(number -> number % 2 == 0).filter(number -> number % 2 == 0).subscribe(System.out::println);

        System.out.println();

        //fibonacciGenerator.subscribe(t -> System.out.println(t));
        System.out.println();

        fibonacciGenerator.filterWhen(a -> Mono.just(a < 10)).subscribe(t -> System.out.println(t));

        System.out.println();
        fibonacciGenerator.filterWhen(number -> Mono.just(number % 2 != 0)).subscribe(number -> System.out.println(number));
                          //.subscribe(number -> System.out::println);
    }


    @Test
    public void testTakeFibonacciNumber() {

        fibonacciGenerator.take(10).subscribe(number -> System.out.println(number));

        System.out.println();

        fibonacciGenerator.takeLast(10).subscribe(number -> System.out.println(number));

        System.out.println();

        fibonacciGenerator.takeLast(1).subscribe(number -> System.out.println(number));

        System.out.println();

        fibonacciGenerator.last().subscribe(number -> System.out.println(number));
    }


    @Test
    public void testSkipFibonacciNumber() {
        fibonacciGenerator.skip(10).subscribe(number -> System.out.println(number));

        System.out.println();

        fibonacciGenerator.skip(Duration.ofMillis(10)).subscribe(number -> System.out.println(number));

        System.out.println();

        fibonacciGenerator.skipUntil(t -> t > 100).subscribe(number -> System.out.println(number));
        System.out.println();
    }
}
