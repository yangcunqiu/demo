package com.cqyang.demo.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class SynchronizedTest {

    private int a;

    private void add() {
        a++;
    }

    private synchronized void safeAdd() {
        a++;
    }

    // 两个线程累加
    @Test
    public void testAdd() {
        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                add();
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                add();
            }
        }).start();

        ThreadSleepUtil.sleepSecond(1);
        log.info("a: {}", a);
    }

    // 两个线程累加
    @Test
    public void testSafeAdd() {
        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                safeAdd();
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                safeAdd();
            }
        }).start();

        ThreadSleepUtil.sleepSecond(1);
        log.info("a: {}", a);
    }
}
