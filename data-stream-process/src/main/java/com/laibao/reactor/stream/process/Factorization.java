package com.laibao.reactor.stream.process;

import java.util.ArrayList;
import java.util.List;

public class Factorization {

    public List<Integer> findFactor(int number) {
        List<Integer> factors= new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            if (number % i == 0) {
                factors.add(i);
            }
        }
        return factors;
    }
}
