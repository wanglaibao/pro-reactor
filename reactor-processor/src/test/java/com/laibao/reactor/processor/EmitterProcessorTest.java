package com.laibao.reactor.processor;

import org.junit.Test;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.FluxSink;

public class EmitterProcessorTest {

    @Test
    public void testEmitterProcessor(){
        EmitterProcessor<Long> emitterProcessor = EmitterProcessor.create();

        emitterProcessor.subscribe(t -> System.out.println(t),
                e -> e.printStackTrace(),
                () -> System.out.println("Finished 1"),
                s -> s.request(1000));

        FluxSink<Long> sink = emitterProcessor.sink();
        sink.next(10L);
        sink.next(11L);
        sink.next(12L);
        //sink.complete();

        emitterProcessor.subscribe(t -> System.out.println(t));
        sink.next(13L);
        sink.next(14L);
        sink.next(15L);

        emitterProcessor.subscribe(t -> System.out.println(t));
        sink.next(16L);
        sink.next(17L);
        sink.next(18L);
    }
}
