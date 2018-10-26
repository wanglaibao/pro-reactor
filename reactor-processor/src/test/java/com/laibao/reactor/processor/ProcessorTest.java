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
}
