package com.cqyang.demo.crawler.medical.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum MedicalEnum {

    FIXED_HOSPITAL(0, "定点医疗机构查询"),
    FIXED_RETAIL_PHARMACY(1, "定点零售药店查询")

    ;

    private final int type;
    private final String desc;
}
