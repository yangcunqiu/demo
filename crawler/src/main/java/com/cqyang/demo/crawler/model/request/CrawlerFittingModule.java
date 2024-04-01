package com.cqyang.demo.crawler.model.request;

import com.cqyang.demo.crawler.model.enums.CrawlerFittingModuleTypeEnum;
import lombok.Data;

/**
 * 爬虫要绑定的处理配置, processor pipeline等
 */
@Data
public class CrawlerFittingModule {

    /**
     * 类型
     * @see CrawlerFittingModuleTypeEnum
     */
    private int type;
    // 标识
    private String code;
}
