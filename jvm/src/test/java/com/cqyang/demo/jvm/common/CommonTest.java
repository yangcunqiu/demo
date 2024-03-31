package com.cqyang.demo.jvm.common;

import org.junit.Test;

public class CommonTest {

    @Test
    public void test() {

        Long num = null;
        long a = num == null ? 0 : num;
        System.out.println(a);

    }
}
