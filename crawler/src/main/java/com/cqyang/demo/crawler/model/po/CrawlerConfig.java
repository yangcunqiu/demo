package com.cqyang.demo.crawler.model.po;

import com.cqyang.demo.crawler.model.enums.CrawlerFittingModuleTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 爬虫的配置, 保存爬虫组装的模块
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CrawlerConfig extends BasicPO{
    private Integer id;
    // 爬虫唯一键
    private Long crawlerUniqueKey;
    /**
     * 组装类型
     * @see CrawlerFittingModuleTypeEnum
     */
    private Integer fittingModuleType;
    // 模块code, 可以配置多个code, 所以这里是集合的json, ["medicalProcessor"]
    private String moduleCodeList;
    // 组装配置json
    private String fittingConfigJson;
}
