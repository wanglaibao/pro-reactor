package com.laibao.reactor.stream.process.test;

import com.laibao.reactor.stream.process.Factorization;
import com.laibao.reactor.stream.process.RomanNumber;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.Comparator;
import java.util.stream.Collectors;

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

    @Test
    public void testMapMethod() {
        RomanNumber numberConvertor= new RomanNumber();
        fibonacciGenerator.skip(1)
                            .take(10)
                            .map(t-> numberConvertor.toRomanNumeral(t.intValue()))
                            .subscribe(t -> System.out.println(t));

        System.out.println();

        Factorization factorization = new Factorization();
        fibonacciGenerator.skip(1).take(10).map(t-> factorization.findFactor(t.intValue())).subscribe(t -> {
            System.out.println(t);
        });
    }

    @Test
    public void testFlatMapMethod() {
        Factorization factorization = new Factorization();
        fibonacciGenerator.skip(1)
                            .take(10)
                            .flatMap(t-> Flux.fromIterable(factorization.findFactor(t.intValue())))
                            .subscribe(t -> System.out.println(t));
    }


    @Test
    public void testRepeatMethod() {
        fibonacciGenerator.take(10).repeat(20).subscribe(number -> System.out.println(number));
    }

    @Test
    public void testCollectionListMethod() {
        fibonacciGenerator.take(100).collectList().subscribe(number -> System.out.println(number));
        System.out.println();

        fibonacciGenerator.take(100).collectSortedList().subscribe(number -> System.out.println(number));
        System.out.println();

        fibonacciGenerator.take(100).collectSortedList(Comparator.reverseOrder()).subscribe(number -> System.out.println(number));
        System.out.println();


        fibonacciGenerator.take(100).collectSortedList((x,y) -> -1 * Long.compare(x,y)).subscribe(number -> System.out.println(number));
        System.out.println();

        fibonacciGenerator.take(100).collect(Collectors.toList()).subscribe(number -> System.out.println(number));
        System.out.println();
    }

    @Test
    public void testCollectionMapMethod() {
        fibonacciGenerator.take(100)
                            .collectMap(t -> t % 2 == 0 ? "even": "odd")
                            //.collectMap(t -> String.valueOf(t))
                            .subscribe(t -> System.out.println(t));
        System.out.println();

        fibonacciGenerator.take(100)
                .collectMultimap(t -> t % 2 == 0 ? "even": "odd")
                //.collectMap(t -> String.valueOf(t))
                .subscribe(t -> System.out.println(t));

    }

    @Test
    public void testReduceMethod() {
        fibonacciGenerator.take(100).count().subscribe(number -> System.out.println(number));
        System.out.println();

        fibonacciGenerator.take(100).reduce((a,b) -> a + b).subscribe(number -> System.out.println(number));
        System.out.println();
    }


    @Test
    public void testAllAndOrConditonsMethod() {
        fibonacciGenerator.take(100).all(x -> x > 100).subscribe(number -> System.out.println(number));
        System.out.println();

        fibonacciGenerator.take(100).any(x -> x > 100).subscribe(number -> System.out.println(number));
        System.out.println();
    }

    @Test
    public void testConcatMethod() {
        fibonacciGenerator.take(20)
                            .concatWith(Flux.just(new Long[]{-1L,-2L,-3L,-4L,-5L,-6L,-7L}))
                            .subscribe(number -> System.out.println(number));
        System.out.println();
        System.out.println();
        System.out.println();
        fibonacciGenerator.take(20)
                .startWith(Flux.just(new Long[]{-1L,-2L,-3L,-4L,-5L,-6L,-7L}))
                .subscribe(number -> System.out.println(number));
        System.out.println();
    }
}
