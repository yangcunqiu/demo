package com.cqyang.demo.crawler.model.vo;

import com.cqyang.demo.crawler.model.enums.CrawlerFittingModuleTypeEnum;
import lombok.Data;

/**
 * 创建和这种爬虫所需要的组件vo
 */
@Data
public class CrawlerFittingModuleVO {

    /**
     * 类型
     * @see CrawlerFittingModuleTypeEnum
     */
    private int type;
    // 标识
    private String code;
    // 类名
    private String className;
    // 描述
    private String desc;
}
