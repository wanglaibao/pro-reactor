package com.laibao.reactor.test;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FluxConvertToCollectionOrMapTest {
    Flux<String> flux = Flux.just(
                                    "Site_0:https://github.com/wanglaibao",
                                    "Description_0:Java8",
                                    "Description_1:Spring5",
                                    "Description_2:Spring Boot",
                                    "Description_3:Kotlin",
                                    "Description_4:Groovy",
                                    "Description_5:Scala");

    @Test
    public void testConvertToList() {
        List<String> stringList = flux.collectList().block();
        stringList.forEach(System.out::println);
    }

    @Test
    public void testConvertToSortedList() {
        List<String> stringList = flux.collectSortedList().block();
        stringList.forEach(System.out::println);
    }

    @Test
    public void testConvertToSet() {
        Set<String> stringList = flux.toStream().collect(Collectors.toSet());
        stringList.forEach(System.out::println);
    }

    @Test
    public void testConvertToMap() {
        Map<String, String> stringMap = flux.collectMap(item -> {return item.split(":")[0];},
                                                    item -> {return item.split(":")[1];})
                                        .block();
        stringMap.forEach((key, value) -> System.out.println(key + " -> " + value));
    }

    @Test
    public void testConvertToMultimap() {

        Map<String, Collection<String>> map = flux.collectMultimap(
                                                     item -> {return item.split("_[0-9]+:")[0];},
                                                     item -> {return item.split(":")[1];})
                                                .block();

        map.forEach((key, value) -> System.out.println(key + " -> " + value));
    }
}
