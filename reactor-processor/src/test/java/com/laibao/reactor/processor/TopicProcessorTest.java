package com.laibao.reactor.processor;

import org.junit.Test;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.TopicProcessor;

import java.util.concurrent.Executors;

public class TopicProcessorTest {

    @Test
    public void testTopicProcessor() {
        TopicProcessor<Long> topicProcessor = TopicProcessor.<Long>builder()
                .executor(Executors.newFixedThreadPool(3)).build();

        topicProcessor.subscribe(t -> System.out.println(t));
        topicProcessor.subscribe(t -> System.out.println(t));
        topicProcessor.subscribe(t -> System.out.println(t));
        FluxSink<Long> sink= topicProcessor.sink();
        sink.next(10L)
            .next(11L)
            .next(12L)
            .next(13L)
            .next(14L)
            .next(15L)
            .next(16L)
            .next(17L)
            .next(18L)
            .next(19L)
            .next(20L);
    }
}
