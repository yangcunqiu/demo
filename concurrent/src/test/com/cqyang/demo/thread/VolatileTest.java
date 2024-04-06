package com.cqyang.demo.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class VolatileTest {
    private boolean flag;
    private volatile boolean flag2;

    @Test
    public void testPrint() {
        new Thread(() -> {
            while (!flag) {

            }
            log.info("flag: {}", flag);
        }).start();

        ThreadSleepUtil.sleepSecond(1);

        flag = true;
        log.info("main end");
    }

    // volatile保证可见性
    @Test
    public void testVol() {
        new Thread(() -> {
            while (!flag2) {

                notify();
            }
            log.info("flag2: {}", flag2);
        }).start();

        ThreadSleepUtil.sleepSecond(1);

        flag2 = true;
        log.info("main end");
    }
}
