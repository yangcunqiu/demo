package com.cqyang.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;

@Slf4j
public class RetryUtilTest {

    @Test
    public void testRetryOneSuccess() {
        String res = RetryUtil.execWithRetry(() -> "success", 5, 1000);
        log.info("invoke result: {}", res);
    }

    @Test
    public void testRetryRandomSuccess() {
        String res = RetryUtil.execWithRetry(() -> {
            Random random = new Random();
            if (random.nextBoolean()) {
                return "success";
            }
            throw new RuntimeException("invoke fail");
        }, 5, 1000, "testRetryRandomSuccess");
        log.info("retry count success, : {}", RetryUtil.getRetryCountByKey("testRetryRandomSuccess"));
        log.info("invoke result: {}", res);
    }

    @Test
    public void testRetryRandomSuccessAndRetryKey() {
        String res1 = RetryUtil.execWithRetry(() -> {
            Random random = new Random();
            if (random.nextBoolean()) {
                return "success";
            }
            throw new RuntimeException("invoke fail");
        }, 5, 1000, "testRetryRandomSuccessAndRetryKey-1");

        String res2 = RetryUtil.execWithRetry(() -> {
            Random random = new Random();
            if (random.nextBoolean()) {
                return "success";
            }
            throw new RuntimeException("invoke fail");
        }, 5, 1000, "testRetryRandomSuccessAndRetryKey-2");

        log.info("retry success need count, : {}", RetryUtil.getRetryCount());
        log.info("invoke result-1: {}", res1);
        log.info("invoke result-2: {}", res2);
    }

    @Test
    public void testRetryRandomSuccessAndAction() {
        String res = RetryUtil.execWithRetry(() -> {
            Random random = new Random();
            if (random.nextBoolean()) {
                return "success";
            }
            throw new RuntimeException("invoke fail");
        }, 5, 1000, () -> log.info("retry action run..."));
        log.info("invoke result: {}", res);
    }

    @Test
    public void testRetryRandomSuccessAndActionAndRetryKey() {
        String res = RetryUtil.execWithRetry(() -> {
            Random random = new Random();
            if (random.nextBoolean()) {
                return "success";
            }
            throw new RuntimeException("invoke fail");
        }, 5, 1000, () -> log.info("retry action run..."), "testRetryRandomSuccessAndAction");
        log.info("retry success need count, : {}", RetryUtil.getRetryCountByKey("testRetryRandomSuccessAndAction"));
        log.info("invoke result: {}", res);
    }
}
