package com.laibao.reactor.test;

import com.laibao.reactor.FactorialService;
import com.laibao.reactor.Person;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;

public class SimpleFluxTest {

    @Test
    public void testJust() {
        Flux<String> stringFlux = Flux.just("金戈");
        stringFlux.subscribe(System.out::println);
        stringFlux.toStream().forEach(System.out::println);

        Flux.just("Red", "Blue", "Yellow", "Black")
            .subscribe(System.out::println);


        Flux<Person> personFlux = Flux.just(new Person("Rahul"), new Person("Rudra"));

        List<Person> personList = personFlux.toStream().collect(Collectors.toList());
        personList.forEach(System.out::println);

        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");

        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(iterable);
    }

    @Test
    public void testRange() {
        Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3);
        numbersFromFiveToSeven.subscribe(System.out::println);
    }

    @Test
    public void testFrom() {
        Integer[] integers = new Integer[]{1,1,2,3,5,8,13};
        Flux<Integer> integerFlux = Flux.fromArray(integers);
        integerFlux.subscribe(System.out::println);

        List<String> stringList = Arrays.asList("Red", "Blue", "Yellow", "Black");
        Flux<String> stringFlux = Flux.fromIterable(stringList);
        stringFlux.subscribe(System.out::println);

        LongStream longStream = LongStream.range(1,1000);
        Flux<Long> longFlux = Flux.fromStream(longStream.boxed());
        longFlux.subscribe(System.out::println);
    }

    @Test
    public void testEmpty() {
        Flux<String> stringFlux = Flux.empty();
        stringFlux.subscribe(System.out::println);
    }

    @Test
    public void testError() {
        Flux<String> stringFlux = Flux.error(() -> new RuntimeException("出错了啊"));
        stringFlux.subscribe(System.out::println);

        //stringFlux.toStream().forEach(System.out::println);
    }

    @Test
    public void testNever() {
        Flux<String> stringFlux = Flux.never();
        stringFlux.subscribe(System.out::println);
    }

    @Test
    public void testCommonFrom(){
        Flux<String> stringFlux = Flux.from(Flux.just("asdfasdf","asfdasfdasfd","wrewerwer"));
        stringFlux.subscribe(System.out::println);
    }

    @Test
    public void testDefer() {
        Flux<String> stringFlux = Flux.defer(() -> Flux.just("1111111","222222222","3333333","44444444","5555555"));
        stringFlux.subscribe(System.out::println);
    }

    @Test
    public void testFactorialService() {
        FactorialService factorialService = new FactorialService();
        Flux<Double> doubleFlux = factorialService.generateFactorial(4);
        doubleFlux.subscribe(System.out::println);
    }

    @Test
    public void testFactorial() {
        Flux<Double> factorialGenerator = new FactorialService().generateFactorial(10);
        factorialGenerator
                .doOnNext(t -> System.out.println(t))
                .last()
                .subscribe(t -> assertEquals(3628800.0, t, 0.0));
    }
}
