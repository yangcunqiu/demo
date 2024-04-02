package com.cqyang.demo.crawler.model.vo;

import com.cqyang.demo.crawler.model.enums.CrawlerFittingModuleTypeEnum;
import lombok.Data;

import java.util.List;

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

    /**
     * 可组装的模块
     */
    private List<CrawlerModuleVO> moduleVOList;

    /**
     * 组装的配置json
     */
    private String fittingConfigJson;
}
