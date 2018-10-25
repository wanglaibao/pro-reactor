package com.laibao.reactor.test;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.stream.IntStream;

import static reactor.core.publisher.Flux.*;

public class FluxTest {

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
