package com.laibao.reactor.test;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertThat;

public class CompletableFutureCombinationTest {

    private CompletableFuture<List<String>> ifhIds(){
      return null;
    }

    private CompletableFuture<String> ifhName(String str) {
        return null;
    }

    private CompletableFuture<Integer> ifhStat(String string) {
      return null;
    };

    @Test
    public void testCompletableFutureCombination() {
        CompletableFuture<List<String>> ids = ifhIds();

        CompletableFuture<List<String>> result = ids.thenComposeAsync(l -> {
            Stream<CompletableFuture<String>> zip =
                    l.stream().map(i -> {
                        CompletableFuture<String> nameTask = ifhName(i);
                        CompletableFuture<Integer> statTask = ifhStat(i);

                        return nameTask.thenCombineAsync(statTask, (name, stat) -> "Name " + name + " has stats " + stat);
                    });
            List<CompletableFuture<String>> combinationList = zip.collect(Collectors.toList());

            CompletableFuture<String>[] combinationArray = combinationList.toArray(new CompletableFuture[combinationList.size()]);

            CompletableFuture<Void> allDone = CompletableFuture.allOf(combinationArray);

            return allDone.thenApply(v -> combinationList.stream().map(CompletableFuture::join).collect(Collectors.toList()));
        });

        List<String> results = result.join();

    }
}
