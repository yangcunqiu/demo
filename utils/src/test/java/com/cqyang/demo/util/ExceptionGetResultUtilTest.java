package com.cqyang.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ExceptionGetResultUtilTest {

    @Test
    public void testOr() {
        String res = ExceptionGetResultUtil.or(() -> "success", "defaultResult");
        log.info("result: {}", res);
    }

    @Test
    public void testOrWithError() {
        String res = ExceptionGetResultUtil.or(() -> {
            throw new RuntimeException("testOrWithError fail");
        }, "defaultResult");
        log.info("result: {}", res);
    }

    @Test
    public void testOrNull() {
        String res = ExceptionGetResultUtil.orNull(() -> "success");
        log.info("result: {}", res);
    }

    @Test
    public void testOrNullWithError() {
        String res = ExceptionGetResultUtil.orNull(() -> {
            throw new RuntimeException("testOrNullWithError fail");
        });
        log.info("result: {}", res);
    }
}
