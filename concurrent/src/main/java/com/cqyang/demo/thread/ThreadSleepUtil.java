package com.cqyang.demo.thread;

public class ThreadSleepUtil {

    public static void sleepSecond(long second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
