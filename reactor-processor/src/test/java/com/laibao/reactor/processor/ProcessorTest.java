package com.laibao.reactor.processor;

import org.junit.Test;
import reactor.core.publisher.DirectProcessor;

public class ProcessorTest {

    @Test
    public void testDirectProcessor() {
        DirectProcessor<Long> data = DirectProcessor.create();
        data.take(3).subscribe(number -> System.out.println(number));    //订阅事件
        data.onNext(10L);                                                // 发布事件
        data.onNext(11L);                                                // 发布事件
        data.onNext(12L);                                                // 发布事件
    }


    @Test
    public void testDirectProcessorFinishedEvent() {
        DirectProcessor<Long> data = DirectProcessor.create();
        data.subscribe(t -> System.out.println(t),
                       e -> e.printStackTrace(),
                       () -> System.out.println("Finished 1"));
        data.onNext(10L);
        data.onComplete();
        data.onNext(15L);
        data.onNext(13L);
        data.onNext(14L);

        data.subscribe(t -> System.out.println(t),
                       e -> e.printStackTrace(),
                       () -> System.out.println("Finished 2"));

        data.onNext(12L);
    }

    @Test
    public void testDirectProcessorErrorEvent() {
        DirectProcessor<Long> data = DirectProcessor.create();

        data.subscribe(t -> System.out.println(t),
                        e -> e.printStackTrace(),
                        () -> System.out.println("Finished"),
                        s -> s.request(1));
        data.onNext(10L);
        data.onNext(11L);
        data.onNext(12L);
    }
}
