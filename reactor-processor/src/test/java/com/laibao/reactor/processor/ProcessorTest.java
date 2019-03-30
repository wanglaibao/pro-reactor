package com.laibao.reactor.processor;

import org.junit.Test;
import reactor.core.publisher.DirectProcessor;

public class ProcessorTest {

    @Test
    public void testDirectProcessor() {
        DirectProcessor<Long> longDirectProcessor = DirectProcessor.create();
        longDirectProcessor.take(3).subscribe(number -> System.out.println(number));    //订阅事件
        longDirectProcessor.onNext(10L);                                                // 发布事件
        longDirectProcessor.onNext(11L);                                                // 发布事件
        longDirectProcessor.onNext(12L);                                                // 发布事件
        longDirectProcessor.onNext(13L);
    }


    @Test
    public void testDirectProcessorFinishedEvent() {
        DirectProcessor<Long> directProcessor = DirectProcessor.create();
        directProcessor.subscribe(t -> System.out.println(t),
                       e -> e.printStackTrace(),
                       () -> System.out.println("Finished 1"));
        directProcessor.sink()
                        .next(10L)
                        .next(11L)
                        .next(12L)
                        .next(13L)
                        .next(14L)
                        .next(15L)
                        .complete();

        directProcessor.subscribe(t -> System.out.println(t),
                       e -> e.printStackTrace(),
                       () -> System.out.println("Finished 2"));
        directProcessor.onNext(123L);
        directProcessor.onNext(124L);
        directProcessor.onNext(125L);
    }

    @Test
    public void testDirectProcessorErrorEvent() {
        DirectProcessor<Long> directProcessor = DirectProcessor.create();
        directProcessor.subscribe(t -> System.out.println(t),
                        e -> e.printStackTrace(),
                        () -> System.out.println("Finished 111111"),
                        s -> s.request(5));
        directProcessor.onNext(10L);
        directProcessor.onNext(11L);
        directProcessor.onNext(12L);
        directProcessor.onNext(13L);
        directProcessor.onNext(14L);
        directProcessor.onNext(15L);
        directProcessor.onComplete();
        directProcessor.subscribe(t -> System.out.println(t),
                e -> e.printStackTrace(),
                () -> System.out.println("Finished 22222"));
    }
}
