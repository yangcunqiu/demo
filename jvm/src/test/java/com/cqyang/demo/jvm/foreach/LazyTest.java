package com.cqyang.demo.jvm.foreach;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LazyTest {
    @Test
    public void test() {
        ValueLoader<Integer> valueLoader = new ValueLoader() {
            @Override
            public List<Integer> load(long offset, int limit) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(1);
                list.add(2);
                list.add(3);
                return list.stream()
                        .skip(offset)
                        .limit(limit)
                        .collect(Collectors.toList());
            }
        };

        LazyIterator<Integer> lazyIterator = new LazyIterator<>(valueLoader);

        for (Integer integer : lazyIterator) {
            System.out.println(integer);
        }
    }
}
