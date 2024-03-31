package com.cqyang.demo.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
public class RetryUtil {

    private static final Map<String, AtomicInteger> retryKeyMap = new HashMap<>();

    public static <T> T execWithRetry(Supplier<T> supplier, int retryCount, int retrySleep, String retryKey) {
        return execWithRetry(supplier, retryCount, retrySleep, () -> {}, retryKey);
    }

    public static <T> T execWithRetry(Supplier<T> supplier, int retryCount, int retrySleep) {
        return execWithRetry(supplier, retryCount, retrySleep, () -> {}, null);
    }

    public static <T> T execWithRetry(Supplier<T> supplier, int retryCount, int retrySleep, Runnable retryAction) {
        return execWithRetry(supplier, retryCount, retrySleep, retryAction, null);
    }

    public static <T> T execWithRetry(Supplier<T> supplier, int retryCount, int retrySleep, Runnable retryAction, String retryKey) {
        for (int i = 0; i <= retryCount; i++) {
            try {
                return supplier.get();
            } catch (Exception e) {
                if (i != retryCount) {
                    log.info("exec fail, do retry, retryKey: {}, count: {}, err: ", retryKey, i+1, e);
                    if (retrySleep > 0) {
                        try {
                            Thread.sleep(retrySleep);
                        } catch (InterruptedException ex) {
                            log.error("Thread Interrupted when sleep, ", ex);
                            Thread.currentThread().interrupt();
                        }
                    }
                    retryAction.run();
                    if (StringUtils.isNotBlank(retryKey)) {
                        addRetryKey(retryKey);
                    }
                    continue;
                }
                throw e;
            }
        }
        return null;
    }

    public static Map<String, Integer> getRetryCount() {
        return retryKeyMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().get()));
    }

    public static Integer getRetryCountByKey(String retryKey) {
        return Optional.ofNullable(retryKeyMap.get(retryKey)).map(AtomicInteger::get).orElse(0);
    }

    private static void addRetryKey(String retryKey) {
        AtomicInteger count = retryKeyMap.get(retryKey);
        if (Objects.isNull(count)) {
            count = new AtomicInteger();
            retryKeyMap.put(retryKey, count);
        }
        count.incrementAndGet();
    }
}
