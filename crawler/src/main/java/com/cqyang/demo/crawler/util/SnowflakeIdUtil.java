package com.cqyang.demo.crawler.util;

public class SnowflakeIdUtil {

    public static Long nextId() {
        return SnowflakeIdWorker.getInstance().nextId();
    }

}
