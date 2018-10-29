package com.laibao.reactor;

import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FluxConvertToCollectionOrMapMainApp {

    public static void main(String[] args) {

        Flux<String> flux = Flux.just(
                "Site_0:grokonez.com",
                "Description_0:Java Technology",
                "Description_1:Spring Framework");

        System.out.println("=== flux.collectList() ===");
        List<String> list1 = flux.collectList().block();
        list1.forEach(System.out::println);
        System.out.println();

        System.out.println("=== flux.collectSortedList() ===");
        List<String> list2 = flux.collectSortedList().block();
        list2.forEach(System.out::println);
        System.out.println();

        System.out.println("=== flux.collectMap() ===");
        Map<String, String> map1 = flux
                .collectMap(
                        item -> {return item.split(":")[0];},
                        item -> {return item.split(":")[1];})
                .block();
        map1.forEach((key, value) -> System.out.println(key + " -> " + value));
        System.out.println();

        System.out.println("=== flux.collectMultimap() ===");
        Map<String, Collection<String>> map2 = flux
                .collectMultimap(
                        item -> {return item.split("_[0-9]+:")[0];},
                        item -> {return item.split(":")[1];})
                .block();
        map2.forEach((key, value) -> System.out.println(key + " -> " + value));
        System.out.println();
    }
}
