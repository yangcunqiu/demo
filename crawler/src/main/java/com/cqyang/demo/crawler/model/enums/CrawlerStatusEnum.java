package com.cqyang.demo.crawler.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CrawlerStatusEnum {

    INIT(0, "初始化"),
    RUNNING(1, "运行"),
    STOPPED(2, "停止"),
    ;


    private final Integer status;
    private final String desc;
}
