package com.laibao.reactor.processor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.concurrent.CountDownLatch;

public class HotPublisherTest {

    @Test
    public void testHotPublisher() throws Exception {
        final UnicastProcessor<Long> hotSource = UnicastProcessor.create();
        final Flux<Long> hotFlux = hotSource.publish().autoConnect();
        hotFlux.take(5).subscribe(t -> System.out.println("1. " + t));
        CountDownLatch latch = new CountDownLatch(2);
        new Thread(() -> {
            int c1 = 0, c2 = 1;
            while (c1 < 1000) {
                hotSource.onNext(Long.valueOf(c1));
                int sum = c1 + c2;
                c1 = c2;
                c2 = sum;
                if(c1 == 144) {
                    hotFlux.subscribe(t -> System.out.println("2. " + t));
                }
            }
            hotSource.onComplete();
            latch.countDown();
        }).start();
        latch.await();
    }
}
