package com.laibao.reactor.processor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class ColdPublisherTest {

    @Test
    public void testColdPublisher() {
        Flux<Long> fibonacciGenerator = Flux.generate(
                () -> Tuples.<Long, Long>of(0L, 1L),
                (state, sink) -> {
                    sink.next(state.getT1());
                    return Tuples.of(state.getT2(), state.getT1() + state.getT2());
                });

        fibonacciGenerator.take(10).subscribe(t -> System.out.println("1. "+t));

        fibonacciGenerator.take(10).subscribe(t -> System.out.println("2. "+t));
    }
}
