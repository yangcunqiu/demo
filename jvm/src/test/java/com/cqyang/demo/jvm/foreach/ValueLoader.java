package com.cqyang.demo.jvm.foreach;

import java.util.List;

public interface ValueLoader<T> {
    List<T> load(long offset, int limit);
}
