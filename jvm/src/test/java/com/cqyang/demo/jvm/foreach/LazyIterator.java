package com.cqyang.demo.jvm.foreach;

import java.util.*;

public class LazyIterator<T> implements Iterable<T> {

    private final ValueLoader<T> valueLoader;

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    public LazyIterator(ValueLoader<T> valueLoader) {
        this.valueLoader = valueLoader;
    }

    private class Itr implements Iterator<T> {
        private int offset = 0;
        private T t;
        @Override
        public boolean hasNext() {
            if (t != null) {
                return true;
            }
            List<T> loadList = valueLoader.load(offset++, 1);
            if (loadList == null || loadList.size() == 0) {
                return false;
            }
            t = loadList.get(0);
            return true;
        }
        @Override
        public T next() {
            if (!hasNext()) {
                throw new RuntimeException("empty list");
            }
            T result = t;
            t = null;
            return result;
        }
    }


}
