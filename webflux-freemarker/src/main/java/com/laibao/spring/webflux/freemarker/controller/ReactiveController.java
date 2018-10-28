package com.laibao.spring.webflux.freemarker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

@Controller
public class ReactiveController {

    @GetMapping("/freemarker")
    public String handleFreemarker(Model model) {
        model.addAttribute("text", "Hello WebFlux");
        return "home";
    }

    @GetMapping("/numbers")
    public String handleSeries(Model model) {
        Flux<Long> fibonacciGenerator = Flux.generate(() -> Tuples.<Long, Long>of(0L, 1L), (state, sink) -> {
            if (state.getT1() < 0) {
                sink.complete();
            } else {
                sink.next(state.getT1());
            }
            return Tuples.of(state.getT2(), state.getT1() + state.getT2());
        });
        model.addAttribute("series", fibonacciGenerator.take(20));
        return "numbers";
    }

}
