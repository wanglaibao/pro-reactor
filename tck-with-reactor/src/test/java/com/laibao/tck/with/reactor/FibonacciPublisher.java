package com.laibao.tck.with.reactor;


import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class FibonacciPublisher implements Publisher<Long> {

    @Override
    public void subscribe(Subscriber<? super Long> subscriber) {
        long count = 0, a = 0, b = 1;
        while (count < 50) {
            long sum = a + b;
            subscriber.onNext(b);
            a = b;
            b = sum;
            count++;
        }
        subscriber.onComplete();
    }
}
