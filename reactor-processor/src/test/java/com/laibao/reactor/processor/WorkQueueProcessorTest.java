package com.laibao.reactor.processor;

import org.junit.Test;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.WorkQueueProcessor;

public class WorkQueueProcessorTest {

    @Test
    public void testWorkQueueProcessor() {
        WorkQueueProcessor<Long> workQueueProcessor = WorkQueueProcessor.<Long>builder().build();
        workQueueProcessor.subscribe(t -> System.out.println("1. "+t));
        workQueueProcessor.subscribe(t -> System.out.println("2. "+t));
        workQueueProcessor.subscribe(t -> System.out.println("3. "+t));
        workQueueProcessor.subscribe(t -> System.out.println("4. "+t));
        workQueueProcessor.subscribe(t -> System.out.println("5. "+t));

        FluxSink<Long> sink= workQueueProcessor.sink();
        sink.next(10L);
        sink.next(11L);
        sink.next(12L);

        sink.next(13L);
        sink.next(14L);
        sink.next(15L);
        sink.next(16L);
        sink.next(17L);
        sink.next(18L);
        sink.next(19L);
        sink.next(120L);
        sink.next(121L);
        sink.next(122L);
    }
}
