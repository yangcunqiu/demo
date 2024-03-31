package com.cqyang.demo.crawler.util;

import java.io.InputStream;

public class ClasspathFileReadUtil {
    private static final ClassLoader classLoader = ClasspathFileReadUtil.class.getClassLoader();

    public static InputStream getFileAsStream(String path) {
        return classLoader.getResourceAsStream(path);
    }
}
