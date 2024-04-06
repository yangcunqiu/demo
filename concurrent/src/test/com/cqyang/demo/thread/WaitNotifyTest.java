package com.cqyang.demo.thread;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WaitNotifyTest {

    // wait/notify必须在同步代码块中使用
    // wait 阻塞当前线程, 释放锁
    // notify 唤醒当前锁的因为wait所阻塞的线程(随机唤醒一个), notifyAll 唤醒所有
    // 模拟一个连接池简单实现
    class MyConnectPool {
        private final int size = 10;
        private final List<String> pools = new ArrayList<>(size);

        private String getPool() throws InterruptedException {
            return getPool(0);
        }

        private String getPool(long timeout) throws InterruptedException {
            synchronized (pools) {
                if (timeout <= 0) {
                    while (pools.isEmpty()) {
                        pools.wait();
                    }
                    return pools.remove(0);
                } else {
                    // 计算到期时间
                    long remaining = timeout;
                    while (pools.isEmpty() && remaining > 0) {
                        long start = System.currentTimeMillis();
                        pools.wait(remaining);
                        long end = System.currentTimeMillis();
                        remaining -= (end - start); // 更新剩余等待时间
                    }
                    return pools.isEmpty() ? null : pools.remove(0);
                }
            }
        }

        private void put(String str) {
            synchronized (pools) {
                if (pools.size() < size) {
                    pools.add(str);
                    pools.notifyAll();
                }
            }
        }
    }

    @Test
    public void testConnectPool() {
        MyConnectPool pool = new MyConnectPool();
        // 模拟放入连接
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                pool.put("connect-" + i);
            }
        }).start();

        // 模拟拿连接
        for (int i = 0; i < 11; i++) {
            new Thread(() -> {
                try {
                    String connect = pool.getPool();
                    log.info("get connect success, {}", connect);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        ThreadSleepUtil.sleepSecond(10);
    }

    @Test
    public void testConnectPoolTimeout() {
        MyConnectPool pool = new MyConnectPool();
        // 模拟放入连接
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                pool.put("connect-" + i);
            }
        }).start();

        // 模拟拿连接
        for (int i = 0; i < 11; i++) {
            new Thread(() -> {
                try {
                    String connect = pool.getPool(3000);
                    if (StringUtils.isNotBlank(connect)) {
                        log.info("get connect success, {}", connect);
                    } else {
                        log.info("get connect fail, {}", connect);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        ThreadSleepUtil.sleepSecond(10);
    }
}
