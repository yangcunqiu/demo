package com.cqyang.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class ExceptionGetResultUtil {

    public static <T> T orNull(Supplier<T> supplier) {
        return or(supplier, null);
    }

    public static <T> T or(Supplier<T> supplier, T defaultValue) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.error("execute err: ", e);
        }
        return defaultValue;
    }
}
