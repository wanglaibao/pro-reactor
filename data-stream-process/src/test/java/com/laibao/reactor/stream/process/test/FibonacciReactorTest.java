package com.laibao.reactor.stream.process.test;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class FibonacciReactorTest {

    @Test
    public void testFibonacciNumber() {

        Flux<Long> fibonacciGenerator = Flux.generate(() -> Tuples.<Long,Long>of(0L, 1L),
                                                            (state, sink) -> {
                                                                if (state.getT1() < 0) {
                                                                    sink.complete();
                                                                } else {
                                                                    sink.next(state.getT1());
                                                                }
                                                            return Tuples.of(state.getT2(), state.getT1() + state.getT2());
                                        });

        fibonacciGenerator.subscribe(t -> {
                                    System.out.println(t);
        });
    }

}
