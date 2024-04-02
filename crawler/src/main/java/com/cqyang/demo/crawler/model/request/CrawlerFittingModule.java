package com.cqyang.demo.crawler.model.request;

import com.cqyang.demo.crawler.model.enums.CrawlerFittingModuleTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * 爬虫要绑定的处理配置, processor pipeline等
 */
@Data
public class CrawlerFittingModule {

    /**
     * 类型
     * @see CrawlerFittingModuleTypeEnum
     */
    private Integer type;
    // 标识
    private List<String> codeList;
    // 组装配置的json
    private String fittingConfig;
}
