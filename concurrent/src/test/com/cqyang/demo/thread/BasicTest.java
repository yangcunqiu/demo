package com.cqyang.demo.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class BasicTest {

    class MyThread extends Thread {
        @Override
        public void run() {
            log.info("t1...");
        }
    }

    class MyRunnable implements Runnable {
        @Override
        public void run() {
            log.info("t2...");
        }
    }


    // 线程的创建, 只有两种方式
    // 1. 继承Thread, 2. 实现Runnable
    @Test
    public void testCreate() throws InterruptedException {
        log.info("main start");

        // 1 继承Thread 重写run方法
        MyThread myThread = new MyThread();
        myThread.start();

        // 2 实现Runnable 实现run方法
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();

        // 2.1 lambda方式实现Runnable
        new Thread(() -> log.info("t2 lambda")).start();

        ThreadSleepUtil.sleepSecond(1);
        log.info("main end");
    }


    class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "123";
        }
    }

    // 获取线程返回值
    // 实现Callable 实现call方法
    @Test
    public void testCallable() throws ExecutionException, InterruptedException {
        log.info("main start");

        // 实现Callable接口, 使用FutureTask将Callable包装成Runnable让Thread.start()调用
        FutureTask<String> futureTask1 = new FutureTask<>(new MyCallable());
        new Thread(futureTask1).start();
        String result1 = futureTask1.get();
        log.info("callable result: {}", result1);

        // lambda
        FutureTask<String> futureTask2 = new FutureTask<>(() -> "456");
        new Thread(futureTask2).start();
        String result2 = futureTask2.get();
        log.info("callable result: {}", result2);

        ThreadSleepUtil.sleepSecond(1);
        log.info("main end");
    }


    // 调用线程的interrupt()可"中断"线程
    // 不是强制的, 只是给这个线程设置了一个标志位, 可以调isInterrupted()查看是否被中断, 至于线程会不会退出, 要看线程自身有没有逻辑去关注监测这个标志位
    // 这个主要是为了安全的退出一个线程, 让线程自己感知到外界有信号要中断自己了, 自己可以关闭资源等 安全退出, 当然, 如果线程自己没有实现这个逻辑, 外界即使调用interrupt()也没什么用
    @Test
    public void testInterrupt() {
        Thread thread = new Thread(() -> {
            Thread currentThread = Thread.currentThread();
            log.info("flag: {}", currentThread.isInterrupted());
            while (!currentThread.isInterrupted()) {
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    log.info("thread interrupted");
//                }
            }
            log.info("flag: {}", currentThread.isInterrupted());
        });
        thread.start();
        ThreadSleepUtil.sleepSecond(1);
        // 中断
        thread.interrupt();
    }

    // join
    @Test
    public void testJoin() throws InterruptedException {
        Thread t1 = new Thread(() -> log.info("1"));
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("2");
        });
        t2.start();

        Thread t3 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("3");
        });
        t3.start();

        ThreadSleepUtil.sleepSecond(1);
    }
}
