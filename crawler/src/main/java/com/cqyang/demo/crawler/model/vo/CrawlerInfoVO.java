package com.cqyang.demo.crawler.model.vo;

import com.cqyang.demo.crawler.model.enums.CrawlerSceneEnum;
import com.cqyang.demo.crawler.model.enums.CrawlerStatusEnum;
import lombok.Data;

import java.util.List;

@Data
public class CrawlerInfoVO {

    /**
     * 唯一键
     */
    private Long uniqueKey;
    /**
     * 爬虫场景
     * @see CrawlerSceneEnum
     */
    private Integer scene;
    /**
     * 场景描述
     */
    private String sceneDesc;
    /**
     * 爬虫状态
     * @see CrawlerStatusEnum
     */
    private Integer status;

    /**
     * 组装模块list
     */
    private List<CrawlerFittingModuleVO> fittingModuleVOList;
}
