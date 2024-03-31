package com.cqyang.demo.jvm.add;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class AService {
    public int get() {
        return 1;
    }
}

class BService {
    public int get() {
        return 2;
    }
}

class CService {
    public int get() {
        return 3;
    }
}

class TService {
    AService aService = new AService();
    BService bService = new BService();
    CService cService = new CService();

    public int add1() throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(3);
        List<Integer> list = new ArrayList<>();
        new Thread(() -> {
            list.add(aService.get());
            cdl.countDown();
        }).start();
        new Thread(() -> {
            list.add(bService.get());
            cdl.countDown();
        }).start();
        new Thread(() -> {
            list.add(cService.get());
            cdl.countDown();
        }).start();
        // 等待
        cdl.await();
        return list.stream().mapToInt(i -> i).sum();
    }

    public int add2() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> aResult = CompletableFuture.supplyAsync(() -> aService.get());
        CompletableFuture<Integer> bResult = CompletableFuture.supplyAsync(() -> bService.get());
        CompletableFuture<Integer> cResult = CompletableFuture.supplyAsync(() -> cService.get());

        // 等待
        int result = CompletableFuture.allOf(aResult, bResult, cResult)
                .thenApply(v -> aResult.join() + bResult.join() + cResult.join())// 累加
                .get();

        return result;
    }
}
