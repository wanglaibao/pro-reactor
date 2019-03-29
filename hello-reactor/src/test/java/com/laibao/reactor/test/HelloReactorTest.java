package com.laibao.reactor.test;

import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;
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
        Flux<Long> fibonacciGenerator = Flux.create(fluxSink -> {
            long current = 1, prev = 0;
            AtomicBoolean stop = new AtomicBoolean(false);
            fluxSink.onDispose(()->{
                stop.set(true);
                System.out.println("******* Stop Received ****** ");
            });
            while (current > 0) {
                fluxSink.next(current);
                System.out.println("generated " + current);
                long next = current + prev;
                prev = current;
                current = next;
            }
            fluxSink.complete();
        });
        List<Long> fibonacciSeries = new LinkedList<>();
        fibonacciGenerator.take(5).subscribe(t -> {
            System.out.println("consuming " + t);
            fibonacciSeries.add(t);
        });
        //System.out.println(fibonacciSeries);
    }

    @Test
    public void testFibonacci() {
        Flux<Long> fibonacciGenerator = Flux.generate(() -> Tuples.<Long, Long>of(0L, 1L), (state, sink) -> {
                                               sink.next(state.getT1());
                                                System.out.println("generated "+state.getT1());
                                               return Tuples.of(state.getT2(), state.getT1() + state.getT2());
                                        });

        List<Long> fibonacciSeries = new LinkedList<>();
        int size = 50;

//        fibonacciGenerator.take(size).subscribe(t -> {
//            System.out.println("consuming "+t);
//            fibonacciSeries.add(t);
//        });

        System.out.println(fibonacciSeries);
        //fibonacciGenerator.toStream().forEach(System.out::println);

        //No event is consumed
        //fibonacciGenerator.take(1).subscribe();

        //assertEquals( 7778742049L, fibonacciSeries.get(size-1).longValue());

        //Only value events are consumed
        //fibonacciGenerator.take(10).subscribe(t -> System.out.println("consuming " + t));

        // Along with value events, we also print error stack-trace
        //fibonacciGenerator.take(100).subscribe(t -> System.out.println("consuming " + t), e -> e.printStackTrace() );


//        fibonacciGenerator.take(10).subscribe(t -> System.out.println("consuming " + t),
//                                    e -> e.printStackTrace(),
//                                    ()-> System.out.println("Finished"));


        fibonacciGenerator.take(200).subscribe(t -> System.out.println("consuming " + t),
                e -> e.printStackTrace(),
                ()-> System.out.println("Finished"),
                s -> {
                    System.out.println("Subscribed :"+ s);
                    s.request(10);
                    s.cancel();
                });



        fibonacciGenerator.take(1000).subscribe(new BaseSubscriber<Long>() {

            @Override
            public void hookOnSubscribe(Subscription subscription) {
                subscription.request(10);
            }

            @Override
            public void hookOnNext(Long value) {

            }

            @Override
            public void hookOnComplete() {

            }

            @Override
            public void hookOnError(Throwable throwable) {

            }

            @Override
            public void hookOnCancel() {}
        });
    }
}
