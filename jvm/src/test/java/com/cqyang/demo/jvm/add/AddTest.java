package com.cqyang.demo.jvm.add;

import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddTest {

    TService tService = new TService();

    @Test
    public void test() throws InterruptedException, ExecutionException {
        System.out.println(tService.add1());
        System.out.println(tService.add2());
    }

    @Data
    class A {
        Long first;
        Long second;
    }

    @Data
    class B {
        int first;
        long second;
    }

    // 3. 下面方法会打印什么
    private void t3() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        v(1, list);
        v((int)2, list);
        v(3L, list);
    }

    private void v(long obj, List<Integer> list) {
        System.out.println(list.contains(obj));
    }
}
