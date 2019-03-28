package com.laibao.reactor.test;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;

public class HelloReactorTest {

    @Test
    public void testSimpleStringFlux() {
        StringBuilder stringBuilder = new StringBuilder();

        Flux<String> strFlux = Flux.just("Quick", "brown", "fox", "jumped", "over", "the", "wall");

        strFlux.subscribe(strValue -> stringBuilder.append(strValue).append(" "));

        System.out.println(stringBuilder.toString());

        assertEquals("Quick brown fox jumped over the wall ",stringBuilder.toString());
    }

    @Test
    public void testFibonacciFluxSink() {
        Flux<Long> fibonacciGenerator = Flux.create(e -> {
            long current = 1, prev = 0;
            AtomicBoolean stop = new AtomicBoolean(false);
            e.onDispose(()->{
                stop.set(true);
                System.out.println("******* Stop Received ****** ");
            });
            while (current > 0) {
                e.next(current);
                System.out.println("generated " + current);
                long next = current + prev;
                prev = current;
                current = next;
            }
            e.complete();
        });
        List<Long> fibonacciSeries = new LinkedList<>();
        fibonacciGenerator.take(50).subscribe(t -> {
            System.out.println("consuming " + t);
            fibonacciSeries.add(t);
        });
        System.out.println(fibonacciSeries);
    }

    @Test
    public void testFibonacci() {
        Flux<Long> fibonacciGenerator = Flux.generate(() -> Tuples.<Long, Long>of(0L, 1L), (state, sink) -> {
                                               sink.next(state.getT1());
                                                //sink.next(state.getT1());
                                                System.out.println("generated "+state.getT1());
                                               return Tuples.of(state.getT2(), state.getT1() + state.getT2());
                                        });

        List<Long> fibonacciSeries = new LinkedList<>();
        int size = 50;

        fibonacciGenerator.take(size).subscribe(t -> {
            System.out.println("consuming "+t);
            fibonacciSeries.add(t);
        });

        System.out.println(fibonacciSeries);

        assertEquals( 7778742049L, fibonacciSeries.get(size-1).longValue());
    }
}
