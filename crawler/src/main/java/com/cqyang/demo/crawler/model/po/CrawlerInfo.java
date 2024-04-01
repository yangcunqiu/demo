package com.cqyang.demo.crawler.model.po;

import com.cqyang.demo.crawler.model.enums.CrawlerSceneEnum;
import com.cqyang.demo.crawler.model.enums.CrawlerStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 爬虫信息表, 每条记录对应一个Spider爬虫实例
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CrawlerInfo extends BasicPO {
    private Integer id;
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

}
