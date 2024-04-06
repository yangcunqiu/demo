package com.cqyang.demo.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
public class CompletableFutureTest {

    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    @Before
    public void before() {
        log.info("main start");
    }

    @After
    public void waitMainThread() {
        log.info("main end");
        while (true){}
    }

    /**
     * 创建CompletableFuture和获取结果的方法
     */
    // 创建CompletableFuture (创建了只要线程池有空闲线程就会立即执行异步任务)
    // CompletableFuture 下面简写成 cf
    @Test
    public void createCF() throws ExecutionException, InterruptedException {
        // 不指定线程池会使用ForkJoinPool.commonPool作为默认线程池
//        CompletableFuture<Void> runResult = CompletableFuture.runAsync(() -> log.info("runAsync running"));
        // 1. 无返回值
        CompletableFuture<Void> runResult = CompletableFuture.runAsync(() -> log.info("runAsync running"), executor);

        // 2. 有返回值
        CompletableFuture<String> supplyResult = CompletableFuture.supplyAsync(() -> "supplyAsync111", executor);
        String result = supplyResult.get();
        log.info("supplyResult: {}", result);
    }

    // 创建有依赖的cf
    @Test
    public void testCreateDependency() throws ExecutionException, InterruptedException {
        // 1. 零依赖 不依赖其他CompletableFuture来创建新的CompletableFuture
        // (除了上面createCF演示的外在补充2种)
        // 1.1 直接创建一个已完成状态的cf
        CompletableFuture<String> completableFuture = CompletableFuture.completedFuture("result1.1");
        String result = completableFuture.get();
        log.info(result);

        // 1.2 创建一个未完成状态的空cf, 然后再通过complete()等完成他
        // 这个的典型使用场景，就是将回调方法转为CompletableFuture，然后再依赖CompletableFuture的能力进行调用编排
        CompletableFuture<String> cf = new CompletableFuture<>();
        if ("success".equals("")) {
            cf.complete("success");
        } else {
            cf.completeExceptionally(new RuntimeException());
        }

        // 2. 一元依赖 依赖一个CompletableFuture创建新的CompletableFuture


    }


    // 获取cf的结果
    @Test
    public void testGet() throws ExecutionException, InterruptedException {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            return "testSupplyAsync";
        });

        // 1. get(timeout) 指定超时时间, 如果任务抛出异常, 抛出ExecutionException, 如果超时了, 抛出TimeoutException
        try {
            String getTimeoutResult = supplyAsync.get(1, TimeUnit.SECONDS);
            log.info("getTimeoutResult success, {}", getTimeoutResult);
        } catch (TimeoutException e) {
            // 超时了
            log.error("getTimeoutResult timeout!!!");
        }

        // 2. get() 不指定超时时间, get会一直阻塞, 如果任务抛出异常, 抛出ExecutionException
        String getResult = supplyAsync.get();
        log.info("getResult: {}", getResult);

        // 3. getNow(defaultValue) 如果cf此时已经完成了, 返回结果, 否则使用默认值, 不阻塞
        // 如果任务抛出异常, 抛出CompletionException (unchecked), 是不需要处理的
        String defaultValue1 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(5);
            return "sleep 5";
        }).getNow("defaultValue1");
        log.info(defaultValue1);
        String defaultValue2 = CompletableFuture.supplyAsync(() -> {
            return "return now";
        }).getNow("defaultValue2");
        log.info(defaultValue2);

        // 4. join() 阻塞获取结果, 和get()对抛出的异常有细微区别, 如果任务抛出异常, 抛出CompletionException (unchecked), 是不需要处理的
        // CompletionException extends RuntimeException 运行时异常, 调用方可以不用处理, 但可以捕获
        // ExecutionException extends Exception 编译时异常, 调用方必须显示的去处理这个异常
        String join = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            return "joinValue";
        }).join();
        log.info(join);
    }

    /**
     * 任务异步回调方法 这些方法都会再返回一个新的CompletableFuture
     */
    // thenRun/thenRunAsync
    // 不关心上一个任务的执行结果, 接受Runnable
    // Runnable 无入参 无返回值
    @Test
    public void testThenRun() {
//        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
//            log.info("cf...");
//            return "hello";
//        }, executor);
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            log.info("cf...");
            return "hello";
        }, executor);
        // 做完第一个任务后，再做第二个任务
        // 1. thenRun 使用上一个任务的线程池执行任务 (如果上一个异步任务没什么耗时, 这个时候已经完成了), 会使用当前线程来执行thenRun的任务)
        cf.thenRun(() -> log.info("thenRun..."));
        // 2. thenRunSync 使用默认线程池执行异步任务 即ForkJoinPool.commonPool
        cf.thenRunAsync(() -> log.info("thenRunAsync..."));
        // 3. thenRunAsync 使用指定线程池执行异步任务
        cf.thenRunAsync(() -> log.info("thenRunAsync executor..."), executor);
    }

    // thenAccept/thenAcceptSync
    // 依赖上一个任务的执行结果 接受Consumer
    // Consumer 入参: 上一个任务的执行结果 无返回值
    @Test
    public void testThenAccept() {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            log.info("cf...");
            return "hello";
        }, executor);
        // thenAccept
        cf.thenAccept((res) -> log.info("thenAccept res,: {}", res));
        // thenAcceptSync
        cf.thenAcceptAsync((res) -> log.info("thenAcceptAsync res,: {}", res));
        // thenAcceptSync 指定线程池
        cf.thenAcceptAsync((res) -> log.info("thenAcceptAsync executor res,: {}", res), executor);
    }

    // thenApply/thenApplySync
    // 依赖上一个任务的结果, 接受Function
    // Function 入参: 上一个任务的执行结果 返回值: 自己的返回值
    @Test
    public void testThenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            log.info("cf...");
            return "hello";
        }, executor);
        // thenApply
        String result1 = cf.thenApply((res) -> {
            log.info("thenApply res: {}", res);
            return res + " world";
        }).get();
        log.info("result1: {}", result1);
        // thenApplyAsync
        String result2 = cf.thenApplyAsync((res) -> {
            log.info("thenApplyAsync res: {}", res);
            return res + " world";
        }).get();
        log.info("result2: {}", result2);
        // thenApplyAsync 指定线程池
        String result3 = cf.thenApplyAsync((res) -> {
            log.info("thenApplyAsync executor res: {}", res);
            return res + " world";
        }, executor).get();
        log.info("result3: {}", result3);
    }

    // thenCompose/thenComposeSAsync
    // 依赖上一个任务的结果, 接受Function
    // Function 入参: 上一个任务执行结果, 返回值: 一个新的cf
    // 和thenApply相比thenCompose重点在于返回的是一个cf
    @Test
    public void testCompose() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            log.info("cf...");
            return "hello";
        }, executor);
        // thenCompose  async不写了 一样的
        CompletableFuture<String> newCf = cf1.thenCompose(this::getNewCF);
        log.info("---");
        ThreadSleepUtil.sleepSecond(10);
        log.info("---");
        String result = newCf.get();
        log.info("result: {}", result);
    }

    private CompletableFuture<String> getNewCF(String str) {
        return  CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            log.info("new cf...");
            return str + " world";
        }, executor);
    }

    // exceptionally
    // 某个任务发生异常时执行的回调方法, 一般用作补偿, 接受Function
    // Function 入参: 任务抛出的Exception 返回值: 上一个任务的泛型
    @Test
    public void testException() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            log.info("cf...");
            throw new RuntimeException("111");
        }, executor);

        String result = cf.exceptionally((e) -> {
            log.error("err: ", e);
            return "hello world";
        }).get();
        log.info(result);
    }

    // whenComplete/whenCompleteAsync
    // 某个任务执行完成后执行的回调方法, 一般用作记录, 接受BiConsumer
    // BiConsumer 第一个入参: 任务结果, 第二个入参: 任务异常, 无返回值
    // 注意: whenComplete不会对结果产生任何影响, 会返回原始的cf的执行结果, 也就是说, 原始的执行结果或者异常执行完whenComplete后会继续传递下去
    @Test
    public void testComplete() throws ExecutionException, InterruptedException {
        boolean flag = true;
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            log.info("cf...");
            if (flag) {
                return "hello";
            } else {
                throw new RuntimeException();
            }
        }, executor);
        // whenComplete
        String result1 = cf.whenComplete((res, e) -> {
            log.info("whenComplete res: {}", res);
            log.error("whenComplete error: ", e);
        }).get(); // 当抛异常的时候, 调get()会报错的, whenComplete中处理了异常, 异常仍然会被传递到结果的CompletableFuture
        log.info("result1: {}", result1);
        // whenCompleteAsync
        String result2 = cf.whenCompleteAsync((res, e) -> {
            log.info("whenCompleteAsync res: {}", res);
            log.error("whenCompleteAsync error: ", e);
        }).get();
        log.info("result2: {}", result2);
        // whenCompleteAsync 指定线程池
        String result3 = cf.whenCompleteAsync((res, e) -> {
            log.info("whenCompleteAsync executor res: {}", res);
            log.error("whenCompleteAsync executor error: ", e);
        }, executor).get();
        log.info("result3: {}", result3);
    }

    // handle/handleSync
    // 某个任务执行完成后执行的回调方法 接受BiFunction
    // BiFunction 第一个入参: 任务结果, 第二个入参: 任务异常, 返回值: 自己的返回值
    // handle有返回值, 可以处理异常，还可以根据上一个任务的结果或异常来计算新的结果
    @Test
    public void testHandle() throws ExecutionException, InterruptedException {
        boolean flag = true;
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            log.info("cf...");
            if (flag) {
                return "hello";
            } else {
                throw new RuntimeException();
            }
        }, executor);
        // handle
        String result1 = cf.handle((res, e) -> {
            log.info("handle res: {}", res);
            log.error("handle error: ", e);
            return res + " world";
        }).get();
        log.info("result1: {}", result1);
        // handleAsync
        String result2 = cf.handleAsync((res, e) -> {
            log.info("handleAsync res: {}", res);
            log.error("handleAsync error: ", e);
            return res + " world";
        }).get();
        log.info("result2: {}", result2);
        // handleAsync 指定线程池
        String result3 = cf.handleAsync((res, e) -> {
            log.info("handleAsync executor res: {}", res);
            log.error("handleAsync executor error: ", e);
            return res + " world";
        }, executor).get();
        log.info("result3: {}", result3);
    }


    /**
     * 多个任务的组合
     */
    // AND
    // 将两个cf组合起来, 当两个cf都正常完成再执行某任务
    @Test
    public void testAnd() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            log.info("cf1...");
            return "hello";
        }, executor);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            log.info("cf2...");
            return " world";
        }, executor);
        // runAfterBoth/runAfterBothSync 接受CompletionStage和Runnable
        // CompletionStage 需要组合的cf
        // Runnable 无入参 无返回值
        cf2.runAfterBoth(cf1, () -> log.info("runAfterBoth"));
        cf2.runAfterBothAsync(cf1, () -> log.info("runAfterBothAsync"));
        cf2.runAfterBothAsync(cf1, () -> log.info("runAfterBothAsync executor"), executor);

        // thenAcceptBoth/thenAcceptBothSync 接受CompletionStage和BiConsumer
        // CompletionStage 需要组合的cf
        // BiConsumer 第一个入参: cf2的执行结果, 第二个入参: cf1的执行结果, 无返回值
        cf2.thenAcceptBoth(cf1, (res1, res2) -> {
            log.info("thenAcceptBoth res1, {}", res1);
            log.info("thenAcceptBoth res2, {}", res2);
        });
        cf2.thenAcceptBothAsync(cf1, (res1, res2) -> {
            log.info("thenAcceptBothAsync res1, {}", res1);
            log.info("thenAcceptBothAsync res2, {}", res2);
        });
        cf2.thenAcceptBothAsync(cf1, (res1, res2) -> {
            log.info("thenAcceptBothAsync executor res1, {}", res1);
            log.info("thenAcceptBothAsync executor res2, {}", res2);
        }, executor);

        // thenCombine/thenCombineSync 接受CompletionStage和BiFunction
        // CompletionStage 需要组合的cf
        // BiFunction 第一个入参: cf2的执行结果, 第二个入参: cf1的执行结果, 返回值: 自己的返回值
        String result1 = cf2.thenCombine(cf1, (res1, res2) -> {
            log.info("thenCombine res1, {}", res1);
            log.info("thenCombine res2, {}", res2);
            return res2 + res1;
        }).get();
        log.info("result1: {}", result1);
        String result2 = cf2.thenCombineAsync(cf1, (res1, res2) -> {
            log.info("thenCombineAsync res1, {}", res1);
            log.info("thenCombineAsync res2, {}", res2);
            return res2 + res1;
        }).get();
        log.info("result2: {}", result2);
        String result3 = cf2.thenCombineAsync(cf1, (res1, res2) -> {
            log.info("thenCombineAsync executor res1, {}", res1);
            log.info("thenCombineAsync executor res2, {}", res2);
            return res2 + res1;
        }, executor).get();
        log.info("result3: {}", result3);
    }

    // AND + cf抛异常
    // runAfterBoth/thenAcceptBoth/thenCombine 这些全都不会执行
    // 两个任务中只要有一个执行异常，则将该异常信息作为指定任务的执行结果
    // 因为条件是组合的两个cf都正常完成后才执行, 抛异常不算正常完成
    @Test
    public void testAndException() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            log.info("cf1...");
            return "hello";
        }, executor);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            log.info("cf2...");
            throw new RuntimeException();
        }, executor);

        cf2.runAfterBoth(cf1, () -> log.info("runAfterBoth"));
        cf2.thenAcceptBoth(cf1, (res1, res2) -> {
            log.info("thenAcceptBoth res1, {}", res1);
            log.info("thenAcceptBoth res2, {}", res2);
        });
        String result1 = cf2.thenCombine(cf1, (res1, res2) -> {
            log.info("thenCombine res1, {}", res1);
            log.info("thenCombine res2, {}", res2);
            return res2 + res1;
        }).get();
        log.info("result1: {}", result1);
    }

    // OR
    // 将两个cf组合起来, 只要其他任意一个执行完就执行某任务
    @Test
    public void testOr() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            log.info("cf1...");
            return "hello";
        }, executor);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(10);
            log.info("cf2...");
            return " world";
        }, executor);

        // or和and入参都类似的
        // runAfterEither/runAfterEitherAsync async这种不写了, 都是一样的, 带async表示异步
        // 接受CompletionStage和Runnable
        cf2.runAfterEither(cf1, () -> log.info("runAfterEither"));

        // acceptEither/acceptEitherAsync
        // 接受CompletionStage和Consumer (任意一个执行完就执行, 所以这里只需要一个入参就行了)
        cf2.acceptEither(cf1, (res) -> log.info("acceptEither res, {}", res));

        // applyToEither/applyToEitherAsync
        // 接受CompletionStage和Function (任意一个执行完就执行, 所以这里只需要一个入参就行了)
        String result = cf2.applyToEither(cf1, (res) -> {
            log.info("applyToEither res: {}", res);
            return res + "111";
        }).get();
        log.info("result: {}", result);
    }

    // OR + Exception
    // runAfterEither/acceptEither/applyToEither 有时执行 有时不执行
    // 两个任务中只要有一个执行异常，则将该异常信息作为指定任务的执行结果
    // 这要看到底是先返回的异常还是先返回的另一个的结果, 先返回异常, 那他们不会执行, 如果是先返回的结果, 那他们就会执行
    @Test
    public void testOrException() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            log.info("cf1...");
            throw new RuntimeException();
        }, executor);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(10);
            log.info("cf2...");
            return " world";
        }, executor);

        cf2.runAfterEither(cf1, () -> log.info("runAfterEither"));
        cf2.acceptEither(cf1, (res) -> log.info("acceptEither res, {}", res));
        String result = cf2.applyToEither(cf1, (res) -> {
            log.info("applyToEither res: {}", res);
            return res + "111";
        }).get();
        log.info("result: {}", result);
    }


    // ALL
    // allOf()会返回一个新的cf, 所有的cf都执行完成后, 才会返回
    @Test
    public void testAll() {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            log.info("cf1...");
            return "hello";
        }, executor);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(6);
            log.info("cf2...");
            return " world";
        }, executor);
        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(9);
            log.info("cf3...");
            return "!!!";
        }, executor);

        CompletableFuture.allOf(cf1, cf2, cf3).whenComplete(((unused, throwable) -> {
            log.info("res {}", unused);
            log.error("error", throwable);
        }));
    }


    // ALL + EXCEPTION
    // 任意一个cf出现异常, 那么会直接返回, 将异常做为新cf的异常
    @Test
    public void testAllException() {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            log.info("cf1...");
            return "hello";
        }, executor);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(6);
            log.info("cf2...");
            return " world";
        }, executor);
        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(9);
            log.info("cf3...");
            throw new RuntimeException();
        }, executor);

        CompletableFuture.allOf(cf1, cf2, cf3).whenComplete(((unused, throwable) -> {
            log.info("res {}", unused);
            log.error("error", throwable);
        }));
    }

    // ANY
    // anyOf() 当任意一个cf完成时返回
    @Test
    public void testAny() {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            log.info("cf1...");
            return "hello";
        }, executor);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(6);
            log.info("cf2...");
            return " world";
        }, executor);
        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(9);
            log.info("cf3...");
            return "!!!";
        }, executor);

        CompletableFuture.anyOf(cf1, cf2, cf3).whenComplete(((res, throwable) -> {
            log.info("res {}", res);
            log.error("error", throwable);
        }));
    }

    // ANY + EXCEPTION
    // 任意一个cf出现异常, 那么会直接返回, 将异常做为新cf的异常
    @Test
    public void testAnyException() {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(3);
            log.info("cf1...");
            throw new RuntimeException();
        }, executor);
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(6);
            log.info("cf2...");
            return " world";
        }, executor);
        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> {
            ThreadSleepUtil.sleepSecond(9);
            log.info("cf3...");
            return " !!!";
        }, executor);

        CompletableFuture.anyOf(cf1, cf2, cf3).whenComplete(((res, throwable) -> {
            log.info("res {}", res);
            log.error("error", throwable);
        }));
    }
}
