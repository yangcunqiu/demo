package com.cqyang.demo.crawler.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CrawlerStatusEnum {

    AWAIT(0, "等待"),
    RUN(1, "运行"),
    CLOSE(2, "关闭"),
    ;


    private final Integer status;
    private final String desc;
}
