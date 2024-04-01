package com.cqyang.demo.crawler.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
@Getter
public enum CrawlerModuleCodeEnum {

    // processor
    DEFAULT_PROCESSOR("defaultProcessor", "CrawlerDefaultProcessor", "默认processor", CrawlerFittingModuleTypeEnum.PROCESSOR),
    MEDICAL_PROCESSOR("medicalProcessor", "MedicalProcessor", "医保processor", CrawlerFittingModuleTypeEnum.PROCESSOR),

    // pipeline
    DEFAULT_PIPELINE("defaultPipeline", "CrawlerDefaultPipeline", "默认pipeline", CrawlerFittingModuleTypeEnum.PIPELINE),
    MEDICAL_PIPELINE("medicalPipeline", "MedicalPipeline", "医保pipeline", CrawlerFittingModuleTypeEnum.PIPELINE),


    ;

    private final String code;
    private final String className;
    private final String desc;
    private final CrawlerFittingModuleTypeEnum moduleTypeEnum;

    public static CrawlerModuleCodeEnum getByCode(String code) {
        for (CrawlerModuleCodeEnum value : CrawlerModuleCodeEnum.values()) {
            if (StringUtils.equals(value.getCode(), code)) {
                return value;
            }
        }
        return null;
    }
}
