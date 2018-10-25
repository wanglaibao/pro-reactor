package com.laibao.tck.with.reactor;
import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;

public class FibonacciPublisherVerification extends PublisherVerification<Long>{

    public FibonacciPublisherVerification(){
        super(new TestEnvironment());
    }

    @Override
    public Publisher<Long> createPublisher(long longNumber) {
        return new FibonacciPublisher();
    }

    @Override
    public Publisher<Long> createFailedPublisher() {
        return null;
    }
}
