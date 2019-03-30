package com.laibao.spring.reactor.controller;

import com.laibao.spring.reactor.domain.Greeting;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
public class GreetReactiveController {

    //以流的形式输出
    @GetMapping(value = "/greetings",produces = "text/event-stream")
    public Publisher<Greeting> greetingPublisher() {
        Flux<Greeting> greetingFlux = Flux.<Greeting>generate(synchronousSink -> synchronousSink.next(new Greeting("Hello")))
                                            .take(100);
        return greetingFlux;
    }
}
