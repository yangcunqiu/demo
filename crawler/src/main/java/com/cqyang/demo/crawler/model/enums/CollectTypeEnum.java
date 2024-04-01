package com.cqyang.demo.crawler.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CollectTypeEnum {
    COLLECT_LIST(0, "采集列表"),
    COLLECT_DETAIL(0, "采集详情"),

    ;
    private final int type;
    private final String desc;
}
