package com.cqyang.demo.crawler.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CollectStatusEnum {

    WAIT_COLLECT(0, "待采集"),
    SUCCESS_COLLECT(1, "采集成功"),
    FAIL_COLLECT(2, "采集失败"),
    NO_COLLECT(3, "不采集"),

    ;

    private final int status;
    private final String desc;
}
