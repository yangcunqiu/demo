package com.cqyang.demo.crawler.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
@Getter
public enum CrawlerFittingModuleTypeEnum {

    PROCESSOR(0, "process", 0),
    PIPELINE(1, "pipeline", 1),


    ;

    private final int type;
    private final String desc;
    private final Integer sort;


    public static boolean isContain(int type) {
        return Arrays.stream(CrawlerFittingModuleTypeEnum.values()).anyMatch(value -> Objects.equals(value.getType(), type));
    }
}
