package com.laibao.tck.without.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class FibonacciSubscriber implements Subscriber<Long> {

    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(10);
    }

    @Override
    public void onNext(Long fibNumber) {
        System.out.println(fibNumber);
        subscription.cancel();
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        subscription = null;
    }

    @Override
    public void onComplete() {
        System.out.println("finished");
        subscription = null;
    }
}
