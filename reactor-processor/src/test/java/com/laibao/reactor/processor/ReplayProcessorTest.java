package com.laibao.reactor.processor;

import org.junit.Test;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.ReplayProcessor;

public class ReplayProcessorTest {

    @Test
    public void testReplayProcessor() {
        ReplayProcessor<Long> replayProcessor = ReplayProcessor.create();
        replayProcessor.subscribe(t -> System.out.println(t));
        FluxSink<Long> sink = replayProcessor.sink();
        sink.next(10L);
        sink.next(11L);
        sink.next(12L);
        sink.next(13L);
        sink.next(14L);

        replayProcessor.subscribe(t -> System.out.println(t));
        replayProcessor.subscribe(t -> System.out.println(t));
        replayProcessor.subscribe(t -> System.out.println(t));
        replayProcessor.subscribe(t -> System.out.println(t));
        replayProcessor.subscribe(t -> System.out.println(t));
    }
}
