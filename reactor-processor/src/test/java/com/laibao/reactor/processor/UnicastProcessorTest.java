package com.laibao.reactor.processor;

import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.reactivestreams.Processor;
import reactor.core.publisher.UnicastProcessor;

public class UnicastProcessorTest {

    @Test
    public void testUnicastProcessor() {
        UnicastProcessor processor = UnicastProcessor.create();
        processor.take(1000).subscribe(t -> System.out.println(t));
        processor.sink().next(10L).next(11L).next(12L);

        processor.subscribe(System.out::println);
    }

}
